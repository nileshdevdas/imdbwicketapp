package com.vinsys.app;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public abstract class BasePage extends WebPage {
	public BasePage() {
		
		Link moviesLink = new Link("movies") {
			@Override
			public void onClick() {
				setResponsePage(MoviesPage.class);
			}
		};
		Link tvLink = new Link("television") {
			@Override
			public void onClick() {
				setResponsePage(TeleVisionPage.class);
			}
		};
		Link eventLink = new Link("events") {
			@Override
			public void onClick() {
				setResponsePage(EventsPage.class);
			}
		};

		Link loginLink = new Link("login") {
			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		};
		add(moviesLink);
		add(tvLink);
		add(eventLink);
		add(loginLink);
	}
}
