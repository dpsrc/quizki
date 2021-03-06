package com.haxwell.apps.questions.servlets;

/**
 * Copyright 2013,2014 Johnathan E. James - haxwell.org - jj-ccs.com - quizki.com
 *
 * This file is part of Quizki.
 *
 * Quizki is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Quizki is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quizki. If not, see http://www.gnu.org/licenses.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haxwell.apps.questions.constants.Constants;
import com.haxwell.apps.questions.entities.User;
import com.haxwell.apps.questions.managers.UserManager;
import com.haxwell.apps.questions.utils.StringUtil;

/**
 * Servlet implementation class LoginServlet
 */
//@ WebServlet("/secured/AdminProfileAccountServlet")
public class AdminProfileAccountServlet extends AbstractHttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProfileAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String fwdPage = "/secured/admin-profile.jsp";
		
		String button = request.getParameter("button");
		
		if (button == null) button = "";
		
		if (button.equals("Change Password"))
		{
			String newPassword = request.getParameter("newPassword");
			String confirmPassword = request.getParameter("confirmPassword");
			
			List<String> errors = new ArrayList<String>();
			List<String> successes = new ArrayList<String>();
			
			if (StringUtil.isNullOrEmpty(newPassword) || StringUtil.isNullOrEmpty(confirmPassword))
			{
				errors.add("One of the passwords you entered was blank.");
			}
			
			if (!newPassword.equals(confirmPassword))
			{
				errors.add("The two passwords did not match.");
			}
			
			if (newPassword.length() < 6)
			{
				errors.add("password too short.. must be at least 6 characters..");
			}
			
			if (errors.isEmpty())
			{
				User user = (User)request.getSession().getAttribute(Constants.CURRENT_USER_ENTITY);
				
				User userWithChangedPassword = UserManager.changeUserPassword(user, newPassword);
				
				request.getSession().setAttribute(Constants.CURRENT_USER_ENTITY, userWithChangedPassword);
				
				successes.add("Password successfully changed.");
				
				request.setAttribute(Constants.SUCCESS_MESSAGES, successes);
			}
			else
			{
				request.setAttribute(Constants.VALIDATION_ERRORS, errors);
			}
		}
		
		request.getSession().setAttribute("tabIndex", Constants.PROFILE_ACCOUNT_TAB_INDEX);
		
		forwardToJSP(request, response, fwdPage);
	}
}
