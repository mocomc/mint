package com.mocomsys.iip.inhouse.wrapper;

public class ApprovalTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MySingleServiceClient client = null; 

		try {
			client = new MySingleServiceClient("http://stage.samsung.net", "C4021ML43", "C4021ML432344623", "C4021ML43", "C4021ML432344623");
			ApprovalRouteVO[] route = new ApprovalRouteVO[7];
			route[0] = new ApprovalRouteVO("20150303", "0", 0);
			route[1] = new ApprovalRouteVO("20150304", "2", 1);
			route[2] = new ApprovalRouteVO("20150304", "2", 2);
			route[3] = new ApprovalRouteVO("20150306", "1", 3);
			route[4] = new ApprovalRouteVO("20150307", "9", 4);
			route[5] = new ApprovalRouteVO("20150308", "9", 5);
			route[6] = new ApprovalRouteVO("20150308", "9", 6);
			
			client.requestApproval("�ű� IIB �������̽� ��", "<html></html>", "12345678901234567890123456789012", route);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
