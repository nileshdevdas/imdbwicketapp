package com.vinsys.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.DataGridView;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.PropertyPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;

public class LoginPage extends BasePage {

	public LoginPage() {
		// create a object of class user.....
		ModalWindow helpWindow = new ModalWindow("help");
		helpWindow.setPageCreator(new ModalWindow.PageCreator() {
			@Override
			public Page createPage() {
				return new CopyrightPage();
			}
		});
		helpWindow.setTitle(new Model("Help"));
		helpWindow.setOutputMarkupId(true);
		AjaxLink help = new AjaxLink("help_link") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				helpWindow.show(target);
			}
		};
		// Create a instance of the user class
		User user = new User();
		FeedbackPanel feedback_panel = new FeedbackPanel("error_messages");
		Form loginForm = new Form("loginForm");
		Label username_label = new Label("username_label", "Username");
		Label password_label = new Label("password_label", "Password");
		TextField<String> username_textfield = new TextField<String>("username", new PropertyModel(user, "username"));
		username_textfield.add(new UsernameValidator());
		username_textfield.setRequired(true);
		PasswordTextField password_textfiedl = new PasswordTextField("password", new PropertyModel(user, "password"));
		password_textfiedl.setRequired(true);
		Button loginButton = new Button("login_button") {
			@Override
			public void onSubmit() {
				super.onSubmit();
				System.out.println(user.getUsername());
				System.out.println(user.getPassword());
				setResponsePage(HomePage.class);
			}
		};
		loginForm.add(username_label);
		loginForm.add(password_label);
		loginForm.add(username_textfield);
		loginForm.add(password_textfiedl);
		loginForm.add(loginButton);
		add(loginForm);
		add(help);
		add(helpWindow);
		add(feedback_panel);

		// #######################################################################################
		IColumn[] columns2 = new IColumn[2];
		columns2[0] = new PropertyColumn(new Model("Username"), "username", "username");
		columns2[1] = new PropertyColumn(new Model("Password"), "username", "username");
		DefaultDataTable table = new DefaultDataTable("datatable", columns2, new UserDataProvider(), 10);
		add(table);
		// #######################################################################################

		// #####################################################################################
		List<ICellPopulator<User>> columns = new ArrayList<>();
		columns.add(new PropertyPopulator<User>("Username"));
		columns.add(new PropertyPopulator<User>("Password"));
		DataGridView view = new DataGridView("rows", columns, new UserDataProvider());
		add(view);
		// #######################################################################################

	}
}
