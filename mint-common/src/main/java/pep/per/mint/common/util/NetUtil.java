package pep.per.mint.common.util;


import java.lang.reflect.Field; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class NetUtil {

	//static Logger logger;
	public static Logger logger = LoggerFactory.getLogger(NetUtil.class);
	
    public static void putBoolean(byte[] b, int off, boolean val)
    {
        b[off] = (byte) (val ? 1 : 0);
    }

    public static void putChar(byte[] b, int off, char val)
    {
        b[off + 1] = (byte) (val >>> 0);
        b[off + 0] = (byte) (val >>> 8);
    }

    public static void putShort(byte[] b, int off, short val)
    {
        b[off + 1] = (byte) (val >>> 0);        // >>> 3���� left �κ��� 1�� �ƴ� 0���� ä������ ���ش�.
        b[off + 0] = (byte) (val >>> 8);
    }

    public static void putInt(byte[] b, int off, int val)
    {
        b[off + 3] = (byte) (val >>> 0);
        b[off + 2] = (byte) (val >>> 8);
        b[off + 1] = (byte) (val >>> 16);
        b[off + 0] = (byte) (val >>> 24);
    }

    public static void putFloat(byte[] b, int off, float val)
    {
        int i = Float.floatToIntBits(val);
        b[off + 3] = (byte) (i >>> 0);
        b[off + 2] = (byte) (i >>> 8);
        b[off + 1] = (byte) (i >>> 16);
        b[off + 0] = (byte) (i >>> 24);
    }

    public static void putLong(byte[] b, int off, long val)
    {
        b[off + 7] = (byte) (val >>> 0);
        b[off + 6] = (byte) (val >>> 8);
        b[off + 5] = (byte) (val >>> 16);
        b[off + 4] = (byte) (val >>> 24);
        b[off + 3] = (byte) (val >>> 32);
        b[off + 2] = (byte) (val >>> 40);
        b[off + 1] = (byte) (val >>> 48);
        b[off + 0] = (byte) (val >>> 56);
    }

    public static byte[] char2Bytes(char c)
    {
        byte[] b = new byte[2];
        putChar(b,0,c);
//        b[0] = (byte)(0xFF&(c>>8)) ;
//        b[1] = (byte)(0x00FF&c) ;
        return b;
    }

    public static byte[] int2Bytes(int i)
    {
        byte[] b = new byte[4];
        putInt(b,0,i);
//        b[0] = (byte)(0xFF&(i>>24));
//        b[1] = (byte)(0xFF&(i>>16));
//        b[2] = (byte)(0xFF&(i>>8));
//        b[3] = (byte)(0xFF&(i>>0));
        return b;
    }

    public static byte[] short2Bytes(short s)
    {
        byte[] b = new byte[2];
        putShort(b,0,s);
//        b[0] = (byte)(0xFF&(s>>8));
//        b[1] = (byte)(0xFF&(s>>0));
        return b;
    }

    /**
     * long ����� byte�迭�� ����� �ش�.
     * @param l byte �迭�� ������� ��.
     * @return byte[] ��ȯ�� byte �迭.
     */
    public static byte[] long2Bytes(long l)
    {
        byte[] b = new byte[8];
        putLong(b,0,l);
//        b[0] = (byte) (0xFF & (l >> 56));
//        b[1] = (byte) (0xFF & (l >> 48));
//        b[2] = (byte) (0xFF & (l >> 40));
//        b[3] = (byte) (0xFF & (l >> 32));
//        b[4] = (byte) (0xFF & (l >> 24));
//        b[5] = (byte) (0xFF & (l >> 16));
//        b[6] = (byte) (0xFF & (l >> 8));
//        b[7] = (byte) (0xFF & (l >> 0));
        return b;
    }

    public static short bytes2Short(byte[] b)
    {
        return bytes2Short(b,0);
    }

    public static short bytes2Short(byte[] b, int pos)
    {
        if (b == null || pos > (b.length - 2))
        {
            throw new ArrayIndexOutOfBoundsException("short's size must be 2 bytes");
        }
        return (short) (((b[pos + 1] & 0xFF) << 0) +
                        ((b[pos + 0] & 0xFF) << 8));
    }

    public static char bytes2Char(byte[] b)
    {
        return bytes2Char(b, 0);
    }

    public static char bytes2Char(byte[] b, int pos)
    {
        if (b == null || pos > (b.length - 2))
        {
            throw new ArrayIndexOutOfBoundsException("char's size must be 2 bytes");
        }
        return (char) (((b[pos + 1] & 0xFF) << 0) +
                       ((b[pos ] & 0xFF) << 8));
    }


    public static long bytes2Long(byte[] b)
    {
        return bytes2Long(b,0);
    }

    public static long bytes2Long(byte[] b,int pos)
    {
        if ( b == null || pos > (b.length-8))
        {
            throw new ArrayIndexOutOfBoundsException("long's size must be 8 bytes");
        }
        return ( (b[pos  ] & 0xFFL) << 56) |
                ((b[pos+1] & 0xFFL) << 48) |
                ((b[pos+2] & 0xFFL) << 40) |
                ((b[pos+3] & 0xFFL) << 32) |
                ((b[pos+4] & 0xFFL) << 24) |
                ((b[pos+5] & 0xFFL) << 16) |
                ((b[pos+6] & 0xFFL) << 8) |
                ((b[pos+7] & 0xFFL) << 0);
    }

    public static int bytes2Int(byte[] b)
    {
        return bytes2Int(b, 0);
    }

    /**
     * Bid Endian ���� ó���Ѵ�.
     * @param b byte[]
     * @param pos int
     * @return int
     */
    public static int bytes2Int(byte[] b , int pos)
    {
        if ( b == null || pos > (b.length-4))
        {
            throw new ArrayIndexOutOfBoundsException("int's size must be 4 bytes");
        }
        return ( (b[pos  ] & 0xFF) << 24) |
                ((b[pos+1] & 0xFF) << 16) |
                ((b[pos+2] & 0xFF) << 8) |
                ((b[pos+3] & 0xFF) << 0);
    }

    public static char twoByteToChar(byte[] b)
    {
        return twoByteToChar(b, 0);
    }

    public static char twoByteToChar(byte[] b, int pos)
    {
        int c = 0x00;
        c = b[pos] & 0xFF;
        c = c << 8;
        c = c | (b[pos + 1] & 0xFF);
        return (char) c;
    }

    public static int stringIpToInt(String ip)
    {
        int ipAddr = 0;
        String[] ipArr = ip.split("\\.", 4);// CommUtil.split(ip,".");// ip.split("\\.", 4);
//        for (int i = 0; i < ipArr.length; i++)
//        {
//            System.out.println("IP[" + i + "] = " + ipArr[i]);
//        }

//        ipAddr = (0x01000000 * Integer.parseInt(ipArr[0])) |
//        (0x00010000 * Integer.parseInt(ipArr[1])) |
//        (0x00000100 * Integer.parseInt(ipArr[2])) |
//        Integer.parseInt(ipArr[3]);
        ipAddr = (Integer.parseInt(ipArr[0]) << 24) |
                 (Integer.parseInt(ipArr[1]) << 16) |
                 (Integer.parseInt(ipArr[2]) << 8) |
                 Integer.parseInt(ipArr[3]);

        return ipAddr;
    }


    public static void logToHex(byte[] b)
    {
        for (int i = 0; i < b.length; i++)
        {
            if (i % 10 == 0)
                System.out.println("");
            String s = Integer.toHexString((0xFF & b[i]));
            System.out.print((s.length() == 1 ? "0" + s : s).toUpperCase() + " ");
        }
        System.out.println("");
    }

    public static int reverseByteOrder(int i)
    {
//        return (0xFF&i)<<24 | (0xFF00&i) << 8 | (0xFF0000&i) >> 8  | (0xFF000000&i)>>24;
        byte[] b = int2Bytes(i);
        byte tmp;
        tmp = b[0];
        b[0] = b[3];
        b[3] = tmp;
        tmp = b[1];
        b[1] = b[2];
        b[2] = tmp;
        return bytes2Int(b);
    }

    public static short reverseByteOrder(short s)
    {
//        int sh = 0xFFFF&s;
//        return (short)(sh >> 8 | sh << 8);

        byte[] b = short2Bytes(s);
        byte tmp = b[0];
        b[0] = b[1];
        b[1] = tmp;
        return bytes2Short(b);
    }

    public static long reverseByteOrder(long l)
    {
//        return
//            (0xFFL&l)<<56 |
//            (0xFF00L&l)<<40 |
//            (0xFF0000L&l) <<24 |
//            (0xFF000000L&l) << 8 |
//            (0xFF00000000000000L&l) >> 56 |
//            (0xFF000000000000L&l) >> 40 |
//            (0xFF0000000000L&l) >> 24 |
//            (0xFF00000000L&l) >> 8 ;
        byte[] b = long2Bytes(l);
        // 0 , 7
        byte tmp = b[0];
        b[0] = b[7];
        b[7] = tmp;
        // 1 , 6
        tmp = b[1];
        b[1] = b[6];
        b[6] = tmp;
        // 2, 5
        tmp = b[2];
        b[2] = b[5];
        b[5] = tmp;
        // 3 , 4
        tmp = b[3];
        b[3] = b[4];
        b[4] = tmp;
        return bytes2Long(b);
    }

    public static char reverseByteOrder(char c)
    {
//        int i = c;
//        return (char)(i<<8 | i>>8);
        byte[] b = char2Bytes(c);
        byte tmp = b[0];
        b[0] = b[1];
        b[1] = tmp;
        return bytes2Char(b);
    }

    /**
     * ���������� �ʵ常 byte order�� �ٲ��ش�.
     * ���� JVM �󿡼� ����ȴٴ� �����Ͽ��� ���α׷� �Ѵ�.
     *
     * @param obj byte order�� �ٲٰ��� �ϴ� ��ü.
     * @return ���� ���� �ʾƵ� �ȴ�. ��ݿ��� ����Ǵ°͸� �ƴϸ� �ȴ�.
     */
    public static Object reverseByteOrder(Object obj)
    {
//        if ( obj == null ) return null;
        Field[] fields = obj.getClass().getFields();
        // �⺻Ÿ��.
        for(int i = 0 ; i < fields.length; i++)
        {
            Field fld = fields[i];
            String typeName = fld.getType().getName();

            try
            {
                if ( typeName.equals("int"))
                {
                    int tmpI = fld.getInt(obj);
                    tmpI = reverseByteOrder(tmpI);
                    fld.setInt(obj, tmpI);
                }
                else if (typeName.equals("char"))
                {
                    char tmpC = fld.getChar(obj);
                    tmpC = reverseByteOrder(tmpC);
                    fld.setChar(obj, tmpC);
                }
                else if (typeName.equals("long"))
                {
                    long tmpL = fld.getLong(obj);
                    tmpL = reverseByteOrder(tmpL);
                    fld.setLong(obj, tmpL);
                }
                else if (typeName.equals("short"))
                {
                    short tmpS = fld.getShort(obj);
                    tmpS = reverseByteOrder(tmpS);
                    fld.setShort(obj, tmpS);
                }
                else if (fld.getType().isArray())
                {}
                else if (typeName.endsWith("String"))
                {}
                else if ( !fld.getType().isPrimitive())
                {
                    Object tmpObj = fld.get(obj);
                    tmpObj = reverseByteOrder(tmpObj);
                    fld.set(obj, tmpObj);
                }
            }
            catch(Exception ex)
            {
                logger.debug("",ex);
//                ex.printStackTrace();
            }
        }
        return obj;
    }

    public static String toHexString(byte b)
    {
        String s = Integer.toHexString((0xFF&b));
        return paddingZero(s,2);
    }

    public static String toHexString(short s)
    {
        String ss = Integer.toHexString((0xFFFF&s));
        return paddingZero(ss,4);
    }
    public static String toHexString(char c)
    {
        String s = Integer.toHexString((int)c);
        return paddingZero(s,4);
    }
    public static String toHexString(int i)
    {
        String s = Integer.toHexString(i);
        return paddingZero(s,8);
    }
    public static String toHexString(long l)
    {
        String s = Long.toHexString(l);
        return paddingZero(s,16);
    }
    /**
     * ���ڸ�ŭ ä���ش�.
     * @param s String
     * @param len int
     * @return String
     */
    public static String paddingZero(String s , int len)
    {
        if ( s == null ) return null;
        int srcLen = s.length();
        int diff = len - srcLen;
        if ( diff > 0 )
        {
            switch ( diff )
            {
            case 1 : return "0" + s;
            case 2 : return "00" + s;
            case 3 : return "000" + s;
            case 4 : return "0000" + s;
            case 5 : return "00000" + s;
            case 6 : return "000000" + s;
            case 7 : return "0000000" + s;
            case 8 : return "00000000" + s;
            default : return s;
            }
        }
        else return s;
    }

    public static byte[] string2FixedBytes(String s , int len)
    {
        byte[] b = new byte[len];
        byte[] b1 = s.getBytes();
        for(int i = 0 ; i < b.length; i++) b[i] = 0x00;
        int copyLength = b.length > b1.length ? b1.length : b.length ;

        System.arraycopy(b1,0,b,0,copyLength);
        return b;
    }

    public static byte[] getFixedString(String str , int len)
    {
        byte[] b = new byte[len];

        // src�� null�̸� �� ��鹮�ڿ� �����Ѵ�.
        if ( str == null ) return b;

        byte[] strB = str.getBytes();
        int minLen = Math.min(len,strB.length);
        System.arraycopy(strB,0,b,0,minLen);
        return b;
    }

    /**
     * ���̰� ������ byte �迭�� String�� �־��ش�.
     *
     * @param src String
     * @param dest ä���� Byte �迭
     */
    public static void fillStringToBytes(String src , byte[] dest)
    {
        if ( dest == null ) return ;

        for(int i = 0 ; i < dest.length; i++) dest[i] = 0x00;

        byte[] b = src.getBytes();
        int len = Math.min(b.length,dest.length);
        System.arraycopy(b,0,dest,0,len);
    }

    /**
     * Main for Test.
     * @param args String[]
     */
    public static void main(String[] args)
    {
//        byte[] b = new byte[8];
//        for(int i = 0 ; i < b.length ; i++)
//        {
//            b[i] = (byte)(i+0x11);
//        }
//
//        long l = bytes2Long(b);
//        int i = bytes2Int(b);
//        int i2 = bytes2Int(b,4);
//        System.out.println("l::" + l);
//        System.out.println("l::" + Long.toHexString(l));
//        System.out.println("l::" + Long.toHexString(reverseByteOrder(l)));
//
//        System.out.println("i::" + Integer.toHexString(i));
//        System.out.println("l::" + Long.toHexString(reverseByteOrder(i)));
//        System.out.println("i2::" + Integer.toHexString(i2));
//        System.out.println("l::" + Long.toHexString(reverseByteOrder(i2)));
//
//        byte[] aab = long2Bytes(l);
//        logToHex(aab);
//
//        short ss = (short)0xFF12;
//        System.out.println("0xFF12:" + toHexString(reverseByteOrder(ss)));
//
//        char cc = 0xF1F2;
//        System.out.println("F1F2:" + toHexString(reverseByteOrder(cc)));
//
//        byte[] bb = new byte[20];
//        logToHex(bb);
/*    	byte[] b = {0x00,0x65};
    	short s = bytes2Short(b);
    	System.out.println(s);
    	
    	byte [] sb = short2Bytes(s);
    	System.out.println("hexastring:" + s + ":"+ toHexString(s));*/
    	
    	
    	byte[] data = {0x00,-127};
    	System.out.println(bytes2Short(data));
    }

}