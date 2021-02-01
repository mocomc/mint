package pep.per.mint.front.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pep.per.mint.front.security.DecryptHttpServletRequest;

/**
 * cross-origin request를 지원하기 위한 필터.<p>
 *
 * https://developer.mozilla.org/ko/docs/Web/HTTP/Access_control_CORS
 *
 * @version 1.0.0
 * @since 1.0.0
 */
public class CORSFilter implements Filter {

	/******************************************************************
	 * Encoding Env
	 ******************************************************************/

	/**
	 * 문자열 인코딩을 나타내는 파라메터 명.<p>
	 */
	private final static String PARAM_ENCODING = "encoding";

	/**
	 * 문자열 인코딩 정보가 설정되어 있을 경우 무시할지 여부를 설정한다.<p>
	 */
	private final static String PARAM_IGNORE = "ignore";

	/**
	 * FilterConfig 객체.<p>
	 */
	protected FilterConfig config = null;

	/**
	 * 요청 정보에 설정된 문자열 인코딩 정보를 무시할지 여부.<p>
	 * 기본값을 true.
	 */
	protected boolean ignore = true;
	/**
	 * 문자열 인코딩.<p>
	 */
	protected String encoding = null;



	/******************************************************************
	 * CORS Env
	 ******************************************************************/

	public static enum CORSRequestType {
        /**
         * A simple HTTP request, i.e. it shouldn't be pre-flighted.
         */
        SIMPLE,
        /**
         * A HTTP request that needs to be pre-flighted.
         */
        ACTUAL,
        /**
         * A pre-flight CORS request, to get meta information, before a
         * non-simple HTTP request is sent.
         */
        PRE_FLIGHT,
        /**
         * Not a CORS request, but a normal request.
         */
        NOT_CORS,
        /**
         * An invalid CORS request, i.e. it qualifies to be a CORS request, but
         * fails to be a valid one.
         */
        INVALID_CORS
    }

	/**
     * {@link Collection} of HTTP methods. Case sensitive.
     *
     * @see http://tools.ietf.org/html/rfc2616#section-5.1.1
     */
    public static final Collection<String> HTTP_METHODS = new HashSet<String>(
            Arrays.asList("OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE",
                    "TRACE", "CONNECT"));

    /**
     * {@link Collection} of non-simple HTTP methods. Case sensitive.
     */
    public static final Collection<String> COMPLEX_HTTP_METHODS =
            new HashSet<String>( Arrays.asList("PUT", "DELETE", "TRACE", "CONNECT"));


    /**
     * {@link Collection} of Simple HTTP methods. Case sensitive.
     *
     * @see http://www.w3.org/TR/cors/#terminology
     */
    public static final Collection<String> SIMPLE_HTTP_METHODS =
            new HashSet<String>(Arrays.asList("GET", "POST", "HEAD"));


    /**
     * {@link Collection} of Simple HTTP request headers. Case in-sensitive.
     *
     * @see http://www.w3.org/TR/cors/#terminology
     */
    public static final Collection<String> SIMPLE_HTTP_REQUEST_HEADERS =
            new HashSet<String>(Arrays.asList("Accept", "Accept-Language","Content-Language"));

    /**
     * {@link Collection} of Simple HTTP request headers. Case in-sensitive.
     *
     * @see http://www.w3.org/TR/cors/#terminology
     */
    public static final Collection<String> SIMPLE_HTTP_RESPONSE_HEADERS =
            new HashSet<String>(Arrays.asList("Cache-Control",
                    "Content-Language", "Content-Type", "Expires",
                    "Last-Modified", "Pragma"));

    /**
     * The Origin header indicates where the cross-origin request or preflight
     * request originates from.
     */
    public static final String REQUEST_HEADER_ORIGIN = "Origin";

    /**
     * The Access-Control-Request-Method header indicates which method will be
     * used in the actual request as part of the preflight request.
     */
    public static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD =
            "Access-Control-Request-Method";

    /**
     * The Access-Control-Request-Headers header indicates which headers will be
     * used in the actual request as part of the preflight request.
     */
    public static final String REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS =
            "Access-Control-Request-Headers";

    /**
     * {@link Collection} of Simple HTTP request headers. Case in-sensitive.
     *
     * @see http://www.w3.org/TR/cors/#terminology
     */
    public static final Collection<String> SIMPLE_HTTP_REQUEST_CONTENT_TYPE_VALUES =
            new HashSet<String>(Arrays.asList(
                    "application/x-www-form-urlencoded", "multipart/form-data", "text/plain"));

    /**
     * The Access-Control-Allow-Origin header indicates whether a resource can
     * be shared based by returning the value of the Origin request header in
     * the response.
     */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN =
            "Access-Control-Allow-Origin";

    /**
     * The Access-Control-Allow-Credentials header indicates whether the
     * response to request can be exposed when the omit credentials flag is
     * unset. When part of the response to a preflight request it indicates that
     * the actual request can include user credentials.
     */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS =
            "Access-Control-Allow-Credentials";

    /**
     * The Access-Control-Expose-Headers header indicates which headers are safe
     * to expose to the API of a CORS API specification
     */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS =
            "Access-Control-Expose-Headers";

    /**
     * The Access-Control-Max-Age header indicates how long the results of a
     * preflight request can be cached in a preflight result cache.
     */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE =
            "Access-Control-Max-Age";

    /**
     * The Access-Control-Allow-Methods header indicates, as part of the
     * response to a preflight request, which methods can be used during the
     * actual request.
     */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS =
            "Access-Control-Allow-Methods";

    /**
     * The Access-Control-Allow-Headers header indicates, as part of the
     * response to a preflight request, which header field names can be used
     * during the actual request.
     */
    public static final String RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS =
            "Access-Control-Allow-Headers";

    /**
     * 키 : allowed<p>
     */
    public static final String PARAM_ALLOWED_CORS = "allowed.cors";

    /**
     * 키 : allowed.origins<p>
     */
    public static final String PARAM_ALLOWED_ORIGINS = "allowed.origins";

    /**
     * 키 : support.credentials.<p>
     */
    public static final String PARAM_SUPPORT_CREDENTIALS ="support.credentials";

    /**
     * 키 : exposed.headers.<p>
     */
    public static final String PARAM_EXPOSED_HEADERS = "exposed.headers";

    /**
     * 키 : allowed.headers.<p>
     */
    public static final String PARAM_ALLOWED_HEADERS =  "allowed.headers";

    /**
     * 키 : allowed.methods.<p>
     */
    public static final String PARAM_ALLOWED_METHODS = "allowed.methods";

    /**
     * 키 : preflight.maxage.<p>
     */
    public static final String PARAM_PREFLIGHT_MAXAGE = "preflight.maxage";

    /**
     * 기본 값 : allowed.<p>
     * <pre>*</pre>
     */
    public static final String DEFAULT_ALLOWED_CORS = "false";

    /**
     * 기본 값 : allowed.origins.<p>
     * <pre>*</pre>
     */
    public static final String DEFAULT_ALLOWED_ORIGINS = "*";

    /**
     * 기본 값 : allowed.methods.<p>
     * GET,POST,HEAD,OPTIONS
     */
    public static final String DEFAULT_ALLOWED_HTTP_METHODS = "GET,POST,HEAD,OPTIONS";

    /**
     * 기본 값 : preflight.maxage ( 초단위 ) .
     * 30초가 기본 값이다.
     */
    public static final String DEFAULT_PREFLIGHT_MAXAGE = "1800";

    /**
     * 기본 값 : suppports.credentials.<p>
     * true
     */
    public static final String DEFAULT_SUPPORTS_CREDENTIALS = "true";

    /**
     * 기본 값 : allowed.headers<p>
     * Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers
     */
    public static final String DEFAULT_ALLOWED_HTTP_HEADERS =
            "Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers";


	/**
	 * 필터 정보.
	 */
	private FilterConfig filterConfig;

	/**
	 * CORS 허용 여부.<p>
	 */
	private boolean allowedCORS = false;

	/**
	 * 접근을 허용할 origin들.
	 */
	private final Collection<String> allowedOrigins;

	/**
	 * 모든 접근을 허용할 지 여부.<p>
	 */
	private boolean anyOriginAllowed = false;

	/**
	 * 접근을 허용할 HttpMethod.
	 */
	private final Collection<String> allowedHttpMethods;

	/**
	 * 접근을 허용할 HttpHeader.
	 */
	private final Collection<String> allowedHttpHeaders;

	/**
	 * 응답에 포함시킬 헤더 정보.<p>
	 */
	private final Collection<String> exposedHeaders;

	/**
     * A supports credentials flag that indicates whether the resource supports
     * user credentials in the request. It is true when the resource does and
     * false otherwise.
     */
    private boolean supportsCredentials;

    /**
     * Indicates (in seconds) how long the results of a pre-flight request can
     * be cached in a pre-flight result cache.
     */
    private long preflightMaxAge;

	public CORSFilter() {
		this.allowedOrigins = new HashSet<String>();
        this.allowedHttpMethods = new HashSet<String>();
        this.allowedHttpHeaders = new HashSet<String>();
        this.exposedHeaders = new HashSet<String>();
	}

	/**
	 * 필터를 초기화 한다.<p>
	 */
	public void init(final FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		String enc = filterConfig.getInitParameter(PARAM_ENCODING);
		try {
			new String("").getBytes(enc);
			this.encoding = enc;
		} catch( UnsupportedEncodingException e ) {
			// 시스템 인코딩으로 설정
			this.encoding = null;
		}

		String value = filterConfig.getInitParameter(PARAM_IGNORE);
		if (value == null) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("true")) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("yes")) {
			this.ignore = true;
		} else {
			this.ignore = false;
		}

		// 옵션 설정
		parseOptions();
	}

	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)|| !(response instanceof HttpServletResponse)) {
            throw new ServletException( "CORSFilter doesn't support non-HTTP request or response.");
        }

		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

		if (ignore || (req.getCharacterEncoding() == null)) {
			if( this.encoding != null ) {
				req.setCharacterEncoding(encoding);
				res.setCharacterEncoding(encoding);
			}
		}

		//----------------------------------------------------------------------------
		// 메세지 복호화를 위해 추가됨.
		//----------------------------------------------------------------------------
		HttpServletRequest useReq = req;
		// 암호화된 데이터인지 확인한다.
		String mintEncrypt = req.getHeader("Mint-Encrypt");
		if( mintEncrypt != null && mintEncrypt.equalsIgnoreCase("true")) {
			DecryptHttpServletRequest req2 = new DecryptHttpServletRequest(req);
			useReq = req2;
		}

		//----------------------------------------------------------------------------
		// 응답메세지 압축 설정을 위해 추가됨.
		//----------------------------------------------------------------------------
		HttpServletResponse useRes = res;
		GZIPResponseWrapper gzipResponse = null;
		boolean isGzip = false;

        String acceptEncoding = req.getHeader("Accept-Encoding");
        String mintCompress   = req.getHeader("Mint-Compress");
        if (acceptEncoding != null && ( mintCompress != null && mintCompress.equalsIgnoreCase("true") ) ) {
            if (acceptEncoding.indexOf("gzip") >= 0) {
				gzipResponse = new GZIPResponseWrapper(res);
				useRes = gzipResponse;
				isGzip = true;
            }
        }

        // cors request 타입 확인
        CORSFilter.CORSRequestType requestType = checkRequestType(useReq);

        switch (requestType) {
        case SIMPLE:
        	handleSimpleCORS(useReq, useRes, chain);
        	break;
        case ACTUAL:
        	// Handles an Actual CORS request.
            handleSimpleCORS(useReq, useRes, chain);
        	break;
        case PRE_FLIGHT:
        	// Handles a Pre-flight CORS request.
            handlePreflightCORS(useReq, useRes, chain);
        	break;
        case NOT_CORS:
        	handleNonCORS(useReq, useRes, chain);
        	break;
        default:
        	handleInvalidCORS(useReq, useRes, chain);
        	break;
        }

        if( isGzip && gzipResponse != null ) {
        	gzipResponse.finish();
        }
	}

	/**
	 * SIMPLE 타입에 해당하는 CORS 요청을 처리한다.<p>
	 *
	 * @param request 요청
	 * @param response 응답
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	protected  void handleSimpleCORS(final HttpServletRequest request
			, final HttpServletResponse response, final FilterChain filterChain)
					throws IOException, ServletException{
		final String origin =
                request.getHeader(REQUEST_HEADER_ORIGIN);
        final String method = request.getMethod();

        // Section 6.1.2
        if (!isOriginAllowed(origin)) {
            handleInvalidCORS(request, response, filterChain);
            return;
        }

        if (!allowedHttpMethods.contains(method)) {
            handleInvalidCORS(request, response, filterChain);
            return;
        }

        // Section 6.1.3
        // Add a single Access-Control-Allow-Origin header.
        if (anyOriginAllowed && !supportsCredentials) {
            // If resource doesn't support credentials and if any origin is
            // allowed
            // to make CORS request, return header with '*'.
            response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        } else {
            // If the resource supports credentials add a single
            // Access-Control-Allow-Origin header, with the value of the Origin
            // header as value.
            response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        }

        // Section 6.1.3
        // If the resource supports credentials, add a single
        // Access-Control-Allow-Credentials header with the case-sensitive
        // string "true" as value.
        if (supportsCredentials) {
            response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS,
                    "true");
        }

        // Section 6.1.4
        // If the list of exposed headers is not empty add one or more
        // Access-Control-Expose-Headers headers, with as values the header
        // field names given in the list of exposed headers.
        if ((exposedHeaders != null) && (exposedHeaders.size() > 0)) {
            String exposedHeadersString = join(exposedHeaders, ",");
            response.addHeader( RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS,
                    exposedHeadersString);
        }

        // Forward the request down the filter chain.
        filterChain.doFilter(request, response);
	}

	/**
	 * Preflighted 요청을 처리한다.<p>
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void handlePreflightCORS(final HttpServletRequest request
			, final HttpServletResponse response, final FilterChain filterChain)
					throws IOException, ServletException{

		final String origin = request.getHeader(REQUEST_HEADER_ORIGIN);

        // Section 6.2.2
        if (!isOriginAllowed(origin)) {
            handleInvalidCORS(request, response, filterChain);
            return;
        }

        // Section 6.2.3
        String accessControlRequestMethod =
                request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
        if (accessControlRequestMethod == null
                || (!HTTP_METHODS.contains(accessControlRequestMethod.trim()))) {
            handleInvalidCORS(request, response, filterChain);
            return;
        } else {
            accessControlRequestMethod = accessControlRequestMethod.trim();
        }

        // Section 6.2.4
        String accessControlRequestHeadersHeader =
                request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);
        List<String> accessControlRequestHeaders = new LinkedList<String>();
        if (accessControlRequestHeadersHeader != null
                && !accessControlRequestHeadersHeader.trim().isEmpty()) {
            String[] headers = accessControlRequestHeadersHeader.trim().split(",");
            for (String header : headers) {
                accessControlRequestHeaders.add(header.trim().toLowerCase());
            }
        }

        // Section 6.2.5
        if (!allowedHttpMethods.contains(accessControlRequestMethod)) {
            handleInvalidCORS(request, response, filterChain);
            return;
        }

        // Section 6.2.6
        if (!accessControlRequestHeaders.isEmpty()) {
            for (String header : accessControlRequestHeaders) {
                if (!allowedHttpHeaders.contains(header)) {
                    handleInvalidCORS(request, response, filterChain);
                    return;
                }
            }
        }

        // Section 6.2.7
        if (supportsCredentials) {
            response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        } else {
            if (anyOriginAllowed) {
                response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            } else {
                response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            }
        }

        // Section 6.2.8
        if (preflightMaxAge > 0) {
            response.addHeader( RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE,
                    String.valueOf(preflightMaxAge));
        }

        // Section 6.2.9
        response.addHeader( RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_METHODS,
                accessControlRequestMethod);

        // Section 6.2.10
        if (allowedHttpHeaders != null && !allowedHttpHeaders.isEmpty()) {
            response.addHeader(RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_HEADERS,
                    join(allowedHttpHeaders, ","));
        }

        // Do not forward the request down the filter chain.
	}

	/**
	 * CORS가 아닌 일반 요청이다.
	 *
	 * @param request 요청 객체
	 * @param response 응답 객체
	 * @param filterChain 필터 체인
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void handleNonCORS(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain filterChain)
            throws IOException, ServletException {
        // by pass
        filterChain.doFilter(request, response);
    }

	/**
	 * CORS 요청이 올바르지 않다.<p>
	 *
	 * @param request
	 * @param response
	 * @param filterChain
	 */
	protected void handleInvalidCORS(final HttpServletRequest request,
            final HttpServletResponse response, final FilterChain filterChain) {
		String origin = request.getHeader(REQUEST_HEADER_ORIGIN);
        String method = request.getMethod();
        String accCtrlReqHeader =
                request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS);

        String message =
                "Invalid CORS request; Origin=" + origin + ";Method=" + method;
        if (accCtrlReqHeader != null) {
            message = message + ";Access-Control-Request-Headers="+ accCtrlReqHeader;
        }

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.resetBuffer();
	}

	/**
	 * cors 요청 타입을 확인한다.<p>
	 *
	 * @param request
	 * @return
	 */
	protected CORSRequestType checkRequestType(final HttpServletRequest request) {
		CORSRequestType requestType = CORSRequestType.INVALID_CORS;

		if( request == null ) {
			throw new IllegalArgumentException("HttpServletRequest is null");
		}

		if( this.allowedCORS ) {
			String originHeader = request.getHeader(REQUEST_HEADER_ORIGIN);

			if( originHeader != null ) {
				if( originHeader.isEmpty()) {
					requestType = CORSRequestType.INVALID_CORS;
				} else if( !isValidOrigin(originHeader) ) {
					requestType = CORSRequestType.INVALID_CORS;
				} else {
					String method = request.getMethod();

					//  메소드를 확인한다.
					if (method != null && HTTP_METHODS.contains(method)) {
						if( method.equals("OPTIONS")) {
							String accReqHeader =
	                                request.getHeader(REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD);
	                        if (accReqHeader != null && !accReqHeader.isEmpty()) {
	                            requestType = CORSRequestType.PRE_FLIGHT;
	                        } else if (accReqHeader != null && accReqHeader.isEmpty()) {
	                            requestType = CORSRequestType.INVALID_CORS;
	                        } else {
	                            requestType = CORSRequestType.ACTUAL;
	                        }
						} else if( method.equals("GET") || method.equals("HEAD")) {
							requestType = CORSRequestType.SIMPLE;
						} else if( method.equals("POST")) {
							String contentType = request.getContentType();
	                        if (contentType != null) {
	                        	contentType = contentType.toLowerCase().trim();
	                        	if (SIMPLE_HTTP_REQUEST_CONTENT_TYPE_VALUES.contains(contentType)) {
	                                requestType = CORSRequestType.SIMPLE;
	                            } else {
	                                requestType = CORSRequestType.ACTUAL;
	                            }
	                        }
						} else if( COMPLEX_HTTP_METHODS.contains(method)) {
							requestType = CORSRequestType.ACTUAL;
						}
					}
				}
			} else {
	            requestType = CORSRequestType.NOT_CORS;
	        }
		} else {
			requestType = CORSRequestType.NOT_CORS;
		}

		return requestType;
	}

	/**
	 * origin이 cors 요청이 가능한지 확인한다.<p>
	 *
	 * @param origin
	 * @return true : 접근 가능한 경우,
	 */
	private boolean isOriginAllowed(final String origin) {
        if (anyOriginAllowed) {
            return true;
        }

        //  origin 헤더에 포함되어 있는 값과 비교한다. 대소문자 구분
        return allowedOrigins.contains(origin);
    }

	/**
	 * CORS 관련 옵션을 로드한다.
	 */
	private void parseOptions() throws ServletException{
		// CORS 허용 여부
		String allowedCORS = filterConfig.getInitParameter(PARAM_ALLOWED_CORS);
		if( allowedCORS == null ) {
			allowedCORS = DEFAULT_ALLOWED_CORS;
		}

		if( allowedCORS != null ){
			this.allowedCORS = Boolean.parseBoolean(allowedCORS);
		}

		// 허용가능한 Origin
		String allowedOrigins = filterConfig.getInitParameter(PARAM_ALLOWED_ORIGINS);
		if ( allowedOrigins == null ) {
			allowedOrigins = DEFAULT_ALLOWED_ORIGINS;
		}

		if (allowedOrigins != null) {
            if (allowedOrigins.trim().equals("*")) {
                this.anyOriginAllowed = true;
            } else {
                this.anyOriginAllowed = false;
                Set<String> setAllowedOrigins =
                        parseStringToSet(allowedOrigins);
                this.allowedOrigins.clear();
                this.allowedOrigins.addAll(setAllowedOrigins);

            }
        }

		// 접근 가능한  http method
		String allowedHttpMethods = filterConfig.getInitParameter(PARAM_ALLOWED_METHODS);
		if( allowedHttpMethods == null ) {
			allowedHttpMethods = DEFAULT_ALLOWED_HTTP_METHODS;
		}

		if (allowedHttpMethods != null) {
            Set<String> setAllowedHttpMethods = parseStringToSet(allowedHttpMethods);
            this.allowedHttpMethods.clear();
            this.allowedHttpMethods.addAll(setAllowedHttpMethods);
        }

		// http header
		String allowedHttpHeaders = filterConfig.getInitParameter(PARAM_ALLOWED_HEADERS);
		if( allowedHttpHeaders == null ) {
			allowedHttpHeaders = DEFAULT_ALLOWED_HTTP_HEADERS;
		}
		if (allowedHttpHeaders != null) {
            Set<String> setAllowedHttpHeaders = parseStringToSet(allowedHttpHeaders);
            Set<String> lowerCaseHeaders = new HashSet<String>();
            for (String header : setAllowedHttpHeaders) {
                String lowerCase = header.toLowerCase();
                lowerCaseHeaders.add(lowerCase);
            }
            this.allowedHttpHeaders.clear();
            this.allowedHttpHeaders.addAll(lowerCaseHeaders);
        }

		//
		String exposedHeaders = filterConfig.getInitParameter(PARAM_EXPOSED_HEADERS);
		if (exposedHeaders != null) {
            Set<String> setExposedHeaders = parseStringToSet(exposedHeaders);
            this.exposedHeaders.clear();
            this.exposedHeaders.addAll(setExposedHeaders);
        }

		String supportsCredentials = filterConfig.getInitParameter(PARAM_SUPPORT_CREDENTIALS);
		if( supportsCredentials == null ) {
			supportsCredentials = DEFAULT_SUPPORTS_CREDENTIALS;
		}

		if (supportsCredentials != null) {
            // For any value other then 'true' this will be false.
            this.supportsCredentials = Boolean.parseBoolean(supportsCredentials);
        }

		String preflightMaxAge = filterConfig.getInitParameter(PARAM_PREFLIGHT_MAXAGE);
		if( preflightMaxAge == null ) {
			preflightMaxAge = DEFAULT_PREFLIGHT_MAXAGE;
		}

		if (preflightMaxAge != null) {
            try {
                if (!preflightMaxAge.isEmpty()) {
                    this.preflightMaxAge = Long.parseLong(preflightMaxAge);
                } else {
                    this.preflightMaxAge = 0L;
                }
            } catch (NumberFormatException e) {
                throw new ServletException("Unable to parse preflightMaxAge", e);
            }
        }
	}

	public void destroy() {
		this.encoding = null;
		this.ignore = true;
	}

	/**
	 * origin이 접근가능한 올바른 주소인지를 확인한다.
	 * 만약 인코딩된 문자열을 포함하는 경우 올바르지 않는 주소로 인식한다.
	 *
	 * @param origin
	 * @see <a href="http://tools.ietf.org/html/rfc952">RFC952</a>
	 * @return
	 */
	public static boolean isValidOrigin(String origin) {
		if (origin.contains("%")) {
            return false;
        }

		URI originURI;

        try {
            originURI = new URI(origin);
        } catch (URISyntaxException e) {
            return false;
        }

        // If scheme for URI is null, return false. Return true otherwise.
        return originURI.getScheme() != null;
	}

	/**
	 * Collection의 각 항목을 주어진 딜리미터 기준으로 병한한다.<p>
	 *
	 * @param elements  컬렉션 객체
	 * @param joinSeparator 구분자
	 * @return
	 */
	static String join(final Collection<String> elements, final String joinSeparator) {
		if (elements == null || elements.size() < 1 ) {
            return null;
        }

		String separator = (joinSeparator != null)? joinSeparator : ",";
        StringBuilder buffer = new StringBuilder();
        boolean isFirst = true;
        for (String element : elements) {
            if (!isFirst) {
                buffer.append(separator);
            } else {
                isFirst = false;
            }

            if (element != null) {
                buffer.append(element);
            }
        }

        return buffer.toString();
	}

	/**
	 * ,로 되어 연결되어 있는 문자열을 분리하여 set으로 생성한다.<p>
	 *
	 * @param data ,로 구분된 문자열
	 * @return
	 */
	static Set<String> parseStringToSet(final String data) {
        String[] splits;

        if (data != null && data.length() > 0) {
            splits = data.split(",");
        } else {
            splits = new String[] {};
        }

        Set<String> set = new HashSet<String>();
        if (splits.length > 0) {
            for (String split : splits) {
                set.add(split.trim());
            }
        }

        return set;
    }
}
