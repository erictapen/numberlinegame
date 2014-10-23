package com.wicam.numberlineweb.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;

import de.novanic.eventservice.service.RemoteEventServiceServlet;

public class CustomRemoteServiceServlet extends RemoteServiceServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2437284393378615525L;

	@Override
	protected SerializationPolicy doGetSerializationPolicy(
			HttpServletRequest request, String moduleBaseURL, String strongName) {

		return CustomRemoteServiceServlet.loadSerializationPolicy(this,
				request, moduleBaseURL, strongName);
	}

	/**
	 * Used by HybridServiceServlet.
	 */
	static SerializationPolicy loadSerializationPolicy(HttpServlet servlet,
			HttpServletRequest request, String moduleBaseURL, String strongName) {

		SerializationPolicy serializationPolicy = null;

		String contextPath = request.getContextPath();

		String modulePath = null;
		if (moduleBaseURL != null) {
			try {
				modulePath = new URL(moduleBaseURL).getPath();
			} catch (MalformedURLException ex) {
				// log the information, we will default
				servlet.log("Malformed moduleBaseURL: " + moduleBaseURL, ex);
			}
		}

		if (modulePath == null || !modulePath.startsWith(contextPath)) {

			String contextRelativePath = "numberlineweb/";

			String serializationPolicyFilePath = SerializationPolicyLoader
					.getSerializationPolicyFileName(contextRelativePath
							+ strongName);

			// Open the RPC resource file and read its contents.
			InputStream is = servlet.getServletContext().getResourceAsStream(
					serializationPolicyFilePath);
			try {
				if (is != null) {
					try {
						serializationPolicy = SerializationPolicyLoader
								.loadFromStream(is, null);
						servlet.log("Successfully loaded serialization policy file");
					} catch (ParseException e) {
						servlet.log("ERROR: Failed to parse the policy file '"
								+ serializationPolicyFilePath + "'", e);
					} catch (IOException e) {
						servlet.log("ERROR: Could not read the policy file '"
								+ serializationPolicyFilePath + "'", e);
					}
				} else {
					String message = "ERROR: The serialization policy file '"
							+ serializationPolicyFilePath
							+ "' was not found; did you forget to include it in this deployment?";
					servlet.log(message);
					servlet.log("Failed loading of serialization policy file");
				}
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// Ignore this error
					}
				}
			}
		} else {

			// Strip off the context path from the module base URL. It should be
			// a
			// strict prefix.
			String contextRelativePath = modulePath.substring(contextPath
					.length());

			String serializationPolicyFilePath = SerializationPolicyLoader
					.getSerializationPolicyFileName(contextRelativePath
							+ strongName);

			// Open the RPC resource file and read its contents.
			InputStream is = servlet.getServletContext().getResourceAsStream(
					serializationPolicyFilePath);
			try {
				if (is != null) {
					try {
						serializationPolicy = SerializationPolicyLoader
								.loadFromStream(is, null);
					} catch (ParseException e) {
						servlet.log("ERROR: Failed to parse the policy file '"
								+ serializationPolicyFilePath + "'", e);
					} catch (IOException e) {
						servlet.log("ERROR: Could not read the policy file '"
								+ serializationPolicyFilePath + "'", e);
					}
				} else {
					String message = "ERROR: The serialization policy file '"
							+ serializationPolicyFilePath
							+ "' was not found; did you forget to include it in this deployment?";
					servlet.log(message);
				}
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// Ignore this error
					}
				}
			}

		}

		return serializationPolicy;
	}
}
