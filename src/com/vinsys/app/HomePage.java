package com.vinsys.app;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.resource.ContextRelativeResource;

public class HomePage extends BasePage {

	public HomePage() {
		Fragment fragment1 = new Fragment("contentArea", "formFrag", this);
		fragment1.add(new Label("sp1", "Thisis Fragment1"));
		add(fragment1);

		Fragment fragment2 = new Fragment("contentArea", "messageFrag", this);
		fragment2.add(new Label("sp2", "Fragment2"));
		Image image = new Image("search_icon", new ResourceReference(HomePage.class, "images/top-logo.png"));
		Button clicker = new Button("clicker") {
			public void onSubmit() {
				System.out.println("Replaced...");
				fragment1.setVisible(false);
				replace(fragment2);
				fragment2.setVisible(true);
			};
		};
		clicker.setOutputMarkupId(true);
		Form form = new Form("form1");
		form.add(clicker);
		add(form);
		add(image);
	}
}
