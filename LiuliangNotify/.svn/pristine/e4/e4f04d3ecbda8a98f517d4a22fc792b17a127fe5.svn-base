package com.unisk.notify.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class SendSms {
	/*
	 * submit1.setGateName(GateName); submit1.setItemId(ItemId);
	 * submit1.setSpNumber(spNum); submit1.setUserNumber(mobile);
	 * submit1.setFeeNumber(mobile); submit1.setFeeType("1");
	 * submit1.setReportFlag("1"); submit1.setMsgCode(MsgCode);
	 * submit1.setMsg(smsMsg.trim()); submit1.setItemType("0");
	 */
	public boolean sendsms(String spnumber, String mobile, String msgcode,
			String smsMsg) {
		String strurl = "";
		try {
			strurl = "http://211.94.188.17:8030/Submit%20Gatename=ringplus"
					+ "&CommandId=1&Name=Cmbtur&Pwd=cMBTUR&ItemId=17101&SpNumber="
					+ spnumber + "&UserNumber=" + mobile
					+ "&FeeType=1&MTFlag=0&ReportFlag=0&MsgCode=" + msgcode
					+ "&Msg:=" + enCode((smsMsg).getBytes("gb2312"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		URL url = null;
		BufferedReader in = null;
		String rc = "", tmp = "", strURL = "";
		try {
			url = new URL(strurl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(3000);
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection
					.getInputStream()));
			while (true) {
				tmp = in.readLine();
				if (tmp == null) {
					break;
				}
				rc += tmp;
			}
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 加码函数，将系统用到的控制符变成转义符号
	 * 
	 * @param bsrc
	 *            byte[]
	 * @return String
	 */
	public static String enCode(byte[] bsrc) {
		String dest = "", str;
		byte bb;
		int num;
		if (bsrc == null) {
			return "";
		}
		for (int ii = 0; ii < bsrc.length; ii++) {
			bb = bsrc[ii];
			if (bb >= 0) {
				num = bb;
			} else {
				num = (bb & 0x7F) + (1 << 7);
			}
			str = Integer.toHexString(num);
			if (str.length() < 2) {
				str = "0" + str;
			}
			dest += str.toUpperCase();
		}
		return dest;
	}

	/**
	 * 将加过码的字符串还原
	 * 
	 * @param src
	 *            String
	 * @return byte[]
	 */
	public static byte[] deCode(String src) {
		if (src.length() < 2) {
			return new byte[0];
		}
		byte[] dest = new byte[src.length() / 2];
		byte rb;
		String str;
		Arrays.fill(dest, (byte) 0);
		int index = 0;
		for (int ii = 0; ii < src.length() - 1; ii++) {
			str = "#" + src.substring(ii, ii + 2);
			rb = (byte) Integer.decode(str).intValue();
			dest[index++] = rb;
			ii++;
		}
		return dest;
	}
}
