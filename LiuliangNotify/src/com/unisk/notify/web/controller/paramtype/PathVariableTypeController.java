package com.unisk.notify.web.controller.paramtype;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.unisk.notify.dao.OrderDao;
import com.unisk.notify.model.Order;
import com.unisk.notify.model.SendSms;

@Controller
@RequestMapping("/quanguo/notify")
public class PathVariableTypeController {

	private OrderDao orderDao;
	@RequestMapping(value = "/k/{k}/c/{c}/m/{m}/o/{o}", method = RequestMethod.GET)
	public void test(HttpServletResponse response,
			@PathVariable(value = "k") String k,
			@PathVariable(value = "c") String c,
			@PathVariable(value = "m") String m,
			@PathVariable(value = "o") String o) throws IOException {

		System.out.println(k + ", " + c + ", " + m + ", " + o);
		orderDao.updateorder(k, o, c, m);
		SendSms sendsms = new SendSms();
		Order order = orderDao.selectasynorder(o, k);
		String productname="";
		if (order != null) {
			if(order.getSpuid().equalsIgnoreCase("G00050"))
			{
				productname="50";
			}
			else
			{
				productname="200";
			}
			if ("00000".equalsIgnoreCase(c)) {

				// 把成功短信下发给用户
				sendsms.sendsms("10655123", order.getAccountval(), "15", String
						.format("尊敬的用户,您的号码已成功充入%sMB流量。本流量本月生效,有效期仅限本月。",
								productname));
			} else if ("10016".equalsIgnoreCase(c)) {
				sendsms.sendsms("10655123", order.getAccountval(), "15",
						"抱歉！由于您是2G后付费用户，故无法订购本流量产品，需变更为3G用户方可使用，享受更好上网体验。感谢您的使用。系统已做退费处理，请注意查收！");
			} else if ("10017".equalsIgnoreCase(c)) {
				sendsms.sendsms("10655123", order.getAccountval(), "15",
						"抱歉！由于您是2G预付费用户，因此无法订购本流量产品。感谢您的使用。系统已做退费处理，请注意查收！");
			} else if ("10031".equalsIgnoreCase(c)) {
				sendsms.sendsms("10655123", order.getAccountval(), "15",
						"抱歉！本流量仅支持3G用户购买，4G用户无法订购。感谢您的使用。系统已做退费处理，请注意查收！");
			} else if (m.indexOf("欠费号码") > 0) {
				sendsms.sendsms("10655123", order.getAccountval(), "15",
						"抱歉！由于您有未缴清的套餐费用，因此无法订购本流量产品，缴清套餐费用即可订购。感谢您的使用。");
			}
		}
		// ①表示响应的内容区数据的媒体类型为json格式，且编码为utf-8(客户端应该以utf-8解码)
		response.setContentType("application/json;charset=utf-8");
		// ②写出响应体内容
		String jsonData = "{\"code\":\"1\"}";
		response.getWriter().write(jsonData);
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

}
