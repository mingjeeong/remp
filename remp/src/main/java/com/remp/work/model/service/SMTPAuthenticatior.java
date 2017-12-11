package com.remp.work.model.service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticatior extends Authenticator{
	@Override
    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("leedh93","dlatlxptmxm93!");
    }
}