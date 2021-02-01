/*****************************************************************************
 * Program Name : mint.message.js
 * Description
 *   - UI 에서 사용되는 Message 관리
 *   - Access 방법
 *     mint.message.{함수명};
 *     mint.message.getMessage("CI00001");
 *     mint.message.getMessage("CW00001", "UserId");
 *
 ****************************************************************************/
var mint_message = function() {

};

/**
 * 메시지 정보
 * CIxxxxx :: Info Message
 * CWxxxxx :: Warning Message
 * CExxxxx :: Error Message
 * BIxxxxx :: Business Info Message
 * BWxxxxx :: Business Warning Message
 * BExxxxx :: Business Error Message
 */
mint_message.codes =
{
	'ko' :
		{
			  CI00001 : "저장 하시겠습니까?"
			, CI00002 : "수정 하시겠습니까?"
			, CI00003 : "삭제 하시겠습니까?"
			, CI00004 : "처리 하시겠습니까?"
			, CI00005 : "등록 하시겠습니까?"
			, CI00006 : "승인요청 하시겠습니까?"//TODO: 다국어 (중문) 적용안됨.
			, CI00007 : "승인 하시겠습니까?"//TODO: 다국어 (중문) 적용안됨.
			, CI00008 : "반려 하시겠습니까?"//TODO: 다국어 (중문) 적용안됨.
			, CI00101 : "저장을 완료했습니다"
			, CI00102 : "수정을 완료했습니다"
			, CI00103 : "삭제를 완료했습니다"
			, CI00104 : "처리를 완료했습니다"
			, CI00105 : "등록을 완료했습니다"
			, CI00106 : "승인요청을 완료했습니다"//TODO: 다국어 (중문) 적용안됨.
			, CI00107 : "승인을 완료했습니다"//TODO: 다국어 (중문) 적용안됨.
			, CI00108 : "반려를 완료했습니다"//TODO: 다국어 (중문) 적용안됨.
			, CI00109 : "인터페이스 들여오기를 완료하였습니다.[총 건 수:${}건, 들여온 건 수:${}건, 들여오지 못한 건은 중복 건일 수 있습니다.]"//TODO: 다국어 (중문) 적용안됨.

			, CW00000 : "로그인이 필요합니다"
			, CW00001 : "[${}] 을(를) 입력하여 주십시오"
			, CW00002 : "값을 입력하여 주십시오"
			, CW00003 : "[${}] 을(를) 선택하여 주십시오"
			, CW00004 : "잘못된 요청 페이지 정보가 존재 하여 초기 화면 으로 이동 됩니다"
			, CW00005 : "서비스 아이디 [${}] 에 해당하는 REST-URL 이 존재 하지 않습니다"
			, CW00006 : "[${}] 는 함수가 아니거나 지정된 Scope 에서 찾을 수 없는 함수 입니다"
			, CW00007 : "권한이 없습니다 \n포털 담당자에게 문의하세요"
			, CW00008 : "[${}] 또는 [${}] 을(를) 선택하여 주십시오"
			, CW00009 : "[${}] 에서만 생성 가능합니다."
			, CW00010 : "[${}] 이(가) 없습니다."//TODO: 다국어 (중문) 적용안됨.
			, CW00011 : "[${}] 은(는) 최대 ${}개 까지만 선택가능 합니다."//TODO: 다국어 (중문) 적용안됨.
			, CW00012 : "[${}] 입력값이 올바르지 않습니다. \n영문(소문자/대문자), 숫자, 하이픈(-), 언더바(_) 만 가능합니다."//TODO: 다국어 (중문) 적용안됨.
			, CW00013 : "[${}] \n\n선택한 파일이 비어있습니다.(0 byte) \n\n확인 후 다시 첨부하여 주십시오."//TODO: 다국어 (중문) 적용안됨.
			, CW00014 : "[${}] 을(를) 확인하여 주십시오"  //TODO: 다국어 (중문) 적용안됨.
			, CW00015 : "[${}] 파일명에 다음 항목은 포함될 수 없습니다\n[ !, @, #, $, %, ^, &, * ]"//TODO: 다국어 (중문) 적용안됨.
			, CW00016 : "유효하지 않은 비밀번호입니다.\n영문자 숫자 특수문자를 혼용하여 최소 8자 이상 입력해 주십시오."//TODO: 다국어 (중문) 적용안됨.
			, CW00017 : "비밀번호와 비밀번호확인 값이 일치하지 않습니다."//TODO: 다국어 (중문) 적용안됨.
			, CW00018 : "저장할 정보가 없습니다."//TODO: 다국어 (중문) 적용안됨.
			, CW00019 : "[${}] 정보가 없습니다."//TODO: 다국어 (중문) 적용안됨.
			, CW00021 : "[${}] 이(가) 중복되었습니다."

			, CE10000 : "서버 접속 중 처리에 실패 했습니다. httpStatus : [${}]"
			, CE10001 : "서버와 연결이 종료되었습니다."//TODO: 다국어 (중문) 적용안됨.
			, CE10404 : "서버 접속 경로가 잘못 되었습니다. 경로를 확인해 주십시오"
			, CE12029 : "서버에 접속할 수 없습니다. 잠시 후 다시 요청해 주십시오"
			, CE80000 : "[서비스 타임아웃] 잠시 후 다시 시도하십시오"//TODO: 다국어 (중문) 적용안됨.
			, CE90000 : "[개발자 참고] callback 함수가 정의되지 않았습니다."//TODO: 다국어 (중문) 적용안됨.
			, CE90001 : "[개발자 참고] Websocket serviceCd 가 정의되지 않았습니다"//TODO: 다국어 (중문) 적용안됨.

			, BI00001 : "결재 처리 되었습니다"
			, BI00002 : "선택한 [${}] 사용자를 삭제 하시겠습니까?"
			, BI00003 : "선택한 [${}] 을(를) 삭제 하시겠습니까?"
			, BI00004 : "심의요청 하시겠습니까?"
			, BI00005 : "해당 Comment 를 삭제 하시겠습니까?"
			, BI00006 : "해당 업무를 삭제 하시겠습니까?"
			, BI00007 : "업무를 이관 하시겠습니까?"
			, BI00008 : "담당자 추가를 적용 하시겠습니까?"
			, BI00009 : "담당자 삭제를 적용 하시겠습니까?"
			, BI00010 : "총 [${}] 건 중 [${}] 건 삭제 되었습니다"
			, BI00011 : "업무 이관이 완료되었습니다"
			, BI00012 : "담당자 일괄 편집이 완료되었습니다"
			, BI00013 : "변경된 사항이 없습니다"
			, BI00014 : "결재선 그룹 저장이 완료되었습니다"
			, BI00015 : "결재선 편집 저장이 완료되었습니다"
			, BI00016 : "결재선 그룹 저장을 하시겠습니까?"
			, BI00017 : "결재선 편집 저장을 하시겠습니까?"
			, BI00018 : "해당 도움말을 생성 하시겠습니까?"
			, BI00019 : "배포를 완료했습니다"//TODO: 다국어 (중문) 적용안됨.


			, BW00001 : "중복된 인터페이스가 존재합니다 인터페이스ID [${}]"
			, BW00002 : "[인터페이스 중복] Provider 시스템과 서비스가 동일한 인터페이스가 이미 존재합니다"
			, BW00003 : "전사솔루션 변경 사유를 입력하세요"
			, BW00004 : "이행완료 상태에서 첨부파일이 변경 되었으므로 예정일을 변경해 주세요"
			, BW00005 : "이행완료 상태에서 Consumer가 변경 되었으므로 예정일을 변경해 주세요"
			, BW00006 : "이행완료 상태에서 Provider가 변경 되었으므로 예정일을 변경해 주세요"
			, BW00007 : "이행완료 상태에서 전사솔루션이 변경 되었으므로 예정일을 변경해 주세요"
			, BW00008 : "담당자 - [${}] 의 역할이 중복됩니다 [역할 : ${}]"
			, BW00009 : "인터페이스 정보가 없습니다 \n관리자에게 문의하세요"
			, BW00010 : "인터페이스 맵핑 키 정보가 없습니다 \n전사솔루션 담당자에게 문의하세요"
			, BW00011 : "선택된 시스템이 없습니다"
			, BW00012 : "Consumer / Provider 시스템 관계는 1:1, 1:N, N:1 만 가능합니다"
			, BW00013 : "Consumer / Provider 시스템이 동일 합니다 [${}]"
			, BW00014 : "[${}] 값이 올바르지 않습니다 정확한 값을 선택해 주십시요"
			, BW00015 : "담당자가 선택 되지 않았습니다"
			, BW00016 : "[${}] 담당자가 동일 합니다"
			, BW00017 : "전체 검색의 경우 검색할 사용자 이름을 입력하세요"
			, BW00018 : "기안자는 편집이 불가능 합니다"
			, BW00019 : "결재선 [${}] 는 삭제할 수 없습니다"
			, BW00020 : "결재선 순서를 벗어납니다"
			, BW00021 : "결재선 순서는 다중 변경이 불가능 합니다"
			, BW00022 : "선택된 인터페이스가 없습니다"
			, BW00023 : "개인화 적용 항목은 6개 이상 등록할 수 없습니다"
			, BW00024 : "[${}] 은(는) 2개 이상 추가할 수 없습니다"
			, BW00025 : "장애유형은 3단계까지 선택해야 합니다"
			, BW00026 : "처리상태가 조치완료 이면 조치일자를 입력해야 합니다"
			, BW00027 : "등록할 오류/장애에 관련된 인터페이스를 추가하시기 바랍니다"
			, BW00028 : "처리상태가 조치완료 이면 삭제가 불가능 합니다"
			, BW00029 : "추가할 오류/장애 인터페이스 목록을 선택하여 주십시오"
			, BW00030 : "삭제할 오류/장애 인터페이스 목록을 선택하여 주십시오"
			, BW00031 : "기존 담당자(정) 이 존재합니다"
			, BW00032 : "담당자(정) 이 존재하지 않습니다"
			, BW00033 : "담당자(정)/(부) 교체를 할 수 없습니다"
			, BW00034 : "담당자(정) 은 한명만 가능합니다"
			, BW00035 : "담당자(정) 은 삭제할 수 없습니다"
			, BW00036 : "선택된 사용자가 없습니다"
			, BW00037 : "인터페이스 신규/수정 결재선 및 삭제 결재선은 삭제할 수 없습니다"
			, BW00038 : "선택된 결재선 그룹이 없습니다"
			, BW00039 : "해당 사용자가 결저선에 존재합니다"
			, BW00040 : "등록되지 않은 결재선 그룹입니다 \n그룹을 먼저 등록하십시오"
			, BW00041 : "결재선 그룹이 다중 선택되었습니다 \n하나의 그룹만 선택하십시오"
			, BW00042 : "결재선에 추가된 사용자가 없습니다"
			, BW00043 : "도움말 정보가 없습니다"
			, BW00044 : "선택된 오류/장애 목록이 없습니다"

			, BW00045 : "[${}] 담당자의 [${}] 업무가 동일합니다"
			, BW00046 : "해당 도움말을 수정하시겠습니까?"
			, BW00047 : "서버가 선택 되지 않았습니다"
			, BW00048 : "배포를 실패했습니다"//TODO: 다국어 (중문) 적용안됨.
			, BW00049 : "다음 인터페이스는 배포를 실패했습니다 \n${}"//TODO: 다국어 (중문) 적용안됨.

		}
	,//ko

	'en' :
		{
		      CI00001 : "Do you want to save it?"
			, CI00002 : "Do you want to edit it?"
			, CI00003 : "Are you sure you want to delete?"
			, CI00004 : "Do you want to process it?"
			, CI00005 : "Would you like to register?"
			, CI00006 : "Would you like to request an approval?"
			, CI00007 : "Do you want to approve it?"
			, CI00008 : "Do you want to reject it?"
			, CI00101 : "Saved"
			, CI00102 : "Edited"
			, CI00103 : "Deleted"
			, CI00104 : "Processed"
			, CI00105 : "Registered"
			, CI00106 : "Authorization request complete"
			, CI00107 : "Approved"
			, CI00108 : "Rejected"
			, CI00109 : "I/F imports complete. [The total number of imports: ${}, the number of imports: ${}, ones not imported may have been duplicates.]"

			, CW00000 : "Login is required"
			, CW00001 : "Please enter [${}]"
			, CW00002 : "Please enter a value"
			, CW00003 : "Please select [${}]"
			, CW00004 : "Invalid request page information exists and moves to the initial screen"
			, CW00005 : "REST-URL does not exist for service ID [${}]"
			, CW00006 : "[${}] Is not a function or it can not be found in the specified scope"
			, CW00007 : "No access \ nPlease contact your portal representative"
			, CW00008 : "Please select [${}] or [${}]"
			, CW00009 : "It can be created only in [${}]."
			, CW00010 : "[${}] Does not exist."
			, CW00011 : "[${}] Can only be selected up to ${}."
			, CW00012 : "[${}] The input value is invalid. \ n Alphabet (Lower case / upper case), number, hyphen (-), underscore (_) only."
			, CW00013 : "[${}] \ n \ nThe selected file is empty (0 byte) \ n \ nPlease confirm and attach it again."
			, CW00014 : "Please confirm [${}]"
			, CW00015 : "[${}] Filename can not contain \ n [!, @, #, $,%, ^, &, *]"
			, CW00016 : "Invalid password. \ n Please enter at least 8 characters with a combination of the alphaber, numbers, and special characters."
			, CW00017 : "Password and password verification values do not match."
			, CW00018 : "No information to save"//TODO: 다국어 (중문) 적용안됨.
			, CW00019 : "[${}] No Information"//TODO: 다국어 (중문) 적용안됨.

			, CE10000 : "Connection to server failed. httpStatus: [${}]"
			, CE10001 : "Connection Closed"//TODO: 다국어 (중문) 적용안됨.
			, CE10404 : "Server connection path is invalid. Please check the route"
			, CE12029 : "The server can not be reached. Please try again later"
			, CE80000 : "[Service timeout] Please try again later"
			, CE90000 : "[Developer Notes] The callback function is not defined"
			, CE90001 : "[Developer Notes] The Websocket serviceCd is not defined"

			, BI00001 : "It has been approved."
			, BI00002 : "Are you sure you want to delete the selected user [${}]?"
			, BI00003 : "Are you sure you want to delete the selected [${}]?"
			, BI00004 : "Would you like to ask for deliberation?"
			, BI00005 : "Are you sure you want to delete this comment?"
			, BI00006 : "Are you sure you want to delete this job?"
			, BI00007 : "Would you like to transfer your work?"
			, BI00008 : "Do you want to add contact?"
			, BI00009 : "Do you want to remove contact?"
			, BI00010 : "Total [${}] of [${}] deleted"
			, BI00011 : "Task transfer completed"
			, BI00012 : "Batch editing of contacts is complete"
			, BI00013 : "No changes have been made"
			, BI00014 : "Approver group saved"
			, BI00015 : "Approver editing saved"
			, BI00016 : "Do you want to save approver group?"
			, BI00017 : "Do you want to save the editted approver?"
			, BI00018 : "Would you like to create this help?"
			, BI00019 : "Deployment complete"

			, BW00001 : "Duplicate interface exists Interface ID [${}]"
			, BW00002 : "[Duplicate interface] The interface with the same Provider system and the service already exists"
			, BW00003 : "Enter reason for change of corporate solution"
			, BW00004 : "Please change the due date since  the attachment has been changed in the completion status"
			, BW00005 : "Please change the due date since the Consumer has been changed in the completion status"
			, BW00006 : "Please change the due date since the Provider has been changed in the completion status"
			, BW00007 : "Please change the due date because the solution has been changed in the completion status"
			, BW00008 : "Duplicate roles - [${}] [roles: ${}]"
			, BW00009 : "No interface information available \ n Contact your administrator"
			, BW00010 : "Interface mapping does not have key information \ n Contact your corporate solutions adminstrator"
			, BW00011 : "No system selected"
			, BW00012 : "Consumer / Provider system relationships are only 1: 1, 1: N, N: 1"
			, BW00013 : "Consumer / Provider system is the same [${}]"
			, BW00014 : "[${}] Value is not correct. Please select the correct value"
			, BW00015 : "No contact person selected"
			, BW00016 : "[${}] The same person is in charge"
			, BW00017 : "For full search, enter the username to search."
			, BW00018 : "The drafter cannot edit it"
			, BW00019 : "Approver [${}] can not be deleted"
			, BW00020 : "Approver not in order"
			, BW00021 : "The order of approvers cannot be changed in multiple"
			, BW00022 : "No interface selected"
			, BW00023 : "You can not register more than 6 personalization items"
			, BW00024 : "[${}] can not be added more than one"
			, BW00025 : "You must select up to three levels of error type"
			, BW00026 : "If the processing status is Action Completed, you must enter an action date"
			, BW00027 : "Please add an interface related error / fault to register"
			, BW00028 : "If the processing status is completed, deletion is not possible"
			, BW00029 : "Please select a list of error / fault interfaces to add"
			, BW00030 : "Please select a list of error / fault interfaces to delete"
			, BW00031 : "Existing contact person (main) exists"
			, BW00032 : "The contact (main) person does not exist"
			, BW00033 : "Person in charge (main) / (sub) can not be replaced"
			, BW00034 : "Only one (main) person can be in charge"
			, BW00035 : "Can not delete contact (main)"
			, BW00036 : "No users selected"
			, BW00037 : "Interface new / modified approval line and deleted approval line can not be deleted"
			, BW00038 : "No approver groups selected"
			, BW00039 : "The user exists on the approval line."
			, BW00040 : "Approver group not registered \ nPlease register group first"
			, BW00041 : "Multiple approver group has been selected \ nSelect only one group"
			, BW00042 : "No users added to the approver"
			, BW00043 : "No help information available"
			, BW00044 : "No error / fault list selected"
			, BW00045 : "Task [${}] of the person in charge of [${}] is the same"
			, BW00046 : "Are you sure you want to edit this?"
			, BW00047 : "Server not selected"
			, BW00048 : "Deployment failed"
			, BW00049 : "Deployment of the following interface failed \ n ${}"
		}
	,//en

	'cn' :
		{
			  CI00001 : "是否要保存？"
			, CI00002 : "是否要修改？"
			, CI00003 : "是否要删除？"
			, CI00004 : "是否要处理？"
			, CI00005 : "是否要登录？"
			, CI00101 : "已保存。"
			, CI00102 : "已修改。"
			, CI00103 : "已删除。"
			, CI00104 : "已处理。"
			, CI00105 : "已登录。"

			, CW00000 : "需要登录。"
			, CW00001 : "请输入[${}] 。"
			, CW00002 : "请输入值。"
			, CW00003 : "请选择[${}]。"
			, CW00004 : "因存在错误的邀请页面，将移动到初始界面。"
			, CW00005 : "不存在对应服务ID[${}]的REST-URL。"
			, CW00006 : "[${}]不是函数，或者是在指定的Scope里找不到的函数。"
			, CW00007 : "没有权限。请向\nPortal担当询问。"
			, CW00008 : "请选择[${}] or [${}]。"
			, CW00009 : "It can be created only in [${}]."
			, CW00010 : "[${}] Does not exist."
			, CW00011 : "[${}] Can only be selected up to ${}."
			, CW00012 : "[${}] The input value is invalid. \ n Alphabet (Lower case / upper case), number, hyphen (-), underscore (_) only."
			, CW00013 : "[${}] \ n \ nThe selected file is empty (0 byte) \ n \ nPlease confirm and attach it again."
			, CW00014 : "Please confirm [${}]"
			, CW00015 : "[${}] Filename can not contain \ n [!, @, #, $,%, ^, &, *]"
			, CW00016 : "Invalid password. \ n Please enter at least 8 characters with a combination of the alphaber, numbers, and special characters."
			, CW00017 : "Password and password verification values do not match."
			, CW00018 : "No information to save"//TODO: 다국어 (중문) 적용안됨.
			, CW00019 : "[${}] No Information"//TODO: 다국어 (중문) 적용안됨.

			, CE10000 : "访问服务器失败。 httpStatus : [${}] "
			, CE10001 : "Connection Closed"//TODO: 다국어 (중문) 적용안됨.
			, CE10404 : "服务器链接路径错误。请确认路径。"
			, CE12029 : "无法访问服务器。请稍后再试一下。"

			, BI00001 : "决裁已处理完毕。"
			, BI00002 : "是否要删除所选的[${}] 使用者？"
			, BI00003 : "是否要删除所选的[${}] ？"
			, BI00004 : "是否要进行审议邀请？"
			, BI00005 : "是否要删除对应的Comment？"
			, BI00006 : "是否要删除相应业务？"
			, BI00007 : "是否要转交业务？"
			, BI00008 : "是否要添加担当者？"
			, BI00009 : "是否要删除担当者？"
			, BI00010 : "总[${}] 件数中已删除[${}] 件。"
			, BI00011 : "业务已转交完毕。"
			, BI00012 : "已将担当者统一编辑完了。"
			, BI00013 : "没有变更事项。"
			, BI00014 : "决裁Group保存已完成。"
			, BI00015 : "决裁阶段的编辑内容保存已完成。"
			, BI00016 : "是否要保存决裁Group？"
			, BI00017 : "是否要保存决裁阶段的编辑内容？"
			, BI00018 : "是否要生成相应的帮助信息？"

			, BW00001 : "存在重复的Interface。Interface ID[${}] "
			, BW00002 : "[Interface重复]Provider系统和服务器存在同一个Interface。"
			, BW00003 : "请输入全社Solution变更事由。"
			, BW00004 : "由于在履行完了的状态下，变更了附加文件，因此请修改一下计划日。"
			, BW00005 : "由于在履行完了的状态下，变更了Consumer，因此请修改一下计划日。"
			, BW00006 : "由于在履行完了的状态下，变更了Provider，因此请修改一下计划日。"
			, BW00007 : "由于在履行完了的状态下，变更了全社Solution，因此请修改一下计划日。"
			, BW00008 : "[${}]担当的[${}]作用重复。"
			, BW00009 : "不存在Interface信息。请向\n管理者询问。"
			, BW00010 : "不存在Interface Mapping Key。请向\n全社Solution担当询问。"
			, BW00011 : "不存在所选的系统。"
			, BW00012 : "Consumer&Provider系统关系只能是 1:1, 1:N, N:1的关系。"
			, BW00013 : "Consumer&Provider 系统一致。[${}] "
			, BW00014 : "[${}] 值不正确。请选择正确的值。"
			, BW00015 : "没有选择担当者。"
			, BW00016 : "[${}] 担当一致。"
			, BW00017 : "当进行全部搜索时，请输入要搜索的使用者姓名。"
			, BW00018 : "起草者不可以进行编辑。"
			, BW00019 : "无法删除决裁阶段[${}] 。"
			, BW00020 : "超出决裁顺序。"
			, BW00021 : "不可以变更多个决裁顺序。"
			, BW00022 : "不存在所选的Interface。"
			, BW00023 : "个性化适用项目不能登录6个以上。"
			, BW00024 : "[${}] 不能添加2个以上。"
			, BW00025 : "障碍类型必须选择到3阶段。"
			, BW00026 : "处理状态如果是处理完了的话，需要输入处理日期。"
			, BW00027 : "请添加要登录的报错/障碍相关的Interface。"
			, BW00028 : "处理状态为处理完了的话，无法删除。"
			, BW00029 : "请选择要添加的报错/障碍Interface目录。"
			, BW00030 : "请选择要删除的报错/障碍Interface目录。"
			, BW00031 : "存在原担当者（正）。"
			, BW00032 : "不存在担当者（正）。"
			, BW00033 : "不可以将担当者（正）/（副）更换。"
			, BW00034 : "担当者（正）只能为一个人。"
			, BW00035 : "不能删除担当者（正）。"
			, BW00036 : "不存在所选的使用者。"
			, BW00037 : "无法删除Interface新增/修改决裁阶段以及删除决裁阶段。"
			, BW00038 : "不存在所选的决裁Group。"
			, BW00039 : "决裁阶段里存在该使用者。"
			, BW00040 : "这是未登录的决裁Group。\n请先登录Group。"
			, BW00041 : "已同时选择多个决裁Group。\n请选择一个Group。"
			, BW00042 : "决裁阶段里不存在添加的使用者。"
			, BW00043 : "不存在帮助信息。"
			, BW00044 : "不存在所选的报错/障碍目录。"

			, BW00047 : "服务器不选择。"
		}
	//cn
};



/**
 *  메시지 아이디에 해당하는 메시지를 가져온다
 * @param messageId  MessageID
 * @param param      치환할 문자열
 * @returns
 */
mint_message.prototype.getMessage = function(messageId, param) {
	try {
		var msg = mint_message.codes[mint.lang][messageId];
		if( !msg ) {
			return null; //messageId;
		}

		if( !param ) {
			return msg;
		}
		//  ${} 포함 문자열 대체
		var oMsg = msg;
		var cnt = 0;
		var p = -1;
		var rMsg = "";
		while(true) {
			p = oMsg.indexOf("${}");
			if ( p == -1 ) {
				break;
			}
			rMsg = rMsg + oMsg.substr(0, p);
			if ( param.constructor == Array) {
				rMsg = rMsg + param[cnt];
			} else {
				rMsg = rMsg + param;
			}

			oMsg = oMsg.substr(p+3);
			cnt++;
		}

		rMsg = rMsg + oMsg;
		return rMsg;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.message.getMessage"});
	}
};

/**
 * mint 객체에 추가한다.<p>
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.message === "undefined") {
	        mint.message = new mint_message();
	    }
	} catch( e ) {

	}
});