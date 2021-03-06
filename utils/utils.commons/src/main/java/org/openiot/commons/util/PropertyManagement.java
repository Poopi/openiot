package org.openiot.commons.util;


/**
 *    Copyright (c) 2011-2014, OpenIoT
 *    
 *    This file is part of OpenIoT.
 *
 *    OpenIoT is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Lesser General Public License as published by
 *    the Free Software Foundation, version 3 of the License.
 *
 *    OpenIoT is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License
 *    along with OpenIoT.  If not, see <http://www.gnu.org/licenses/>.
 *
 *     Contact: OpenIoT mailto: info@openiot.eu
 */


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Nikos Kefalakis (nkef) e-mail: nkef@ait.edu.gr
 * 
 */
public class PropertyManagement {
	
	final static Logger logger = LoggerFactory.getLogger(PropertyManagement.class);
	
	
//	// reading proeprty LSM_META_GRAPH
//	String schedulerLsmMetaGraph = "";
//
//	// reading proeprty LSM_DATA_GRAPH
//	String schedulerLsmDataGraph = "";
//
//	// reading proeprty LSM_FUNCTIONAL_GRAPH
//	String schedulerLsmFunctionalGraph = "";
//
//	// reading proeprty LSM_USER_NAME
//	String schedulerLsmUserName = "";
//
//	// reading proeprty LSM_PASSWORD
//	String schedulerLsmPassword = "";
//
//
//	//============SD&UM=============================
//	// reading proeprty LSM_FUNCTIONAL_GRAPH
//	String sdumLsmFunctionalGraph = "";
//	
//	// reading proeprty LSM_SPARQL_END_POINT
//	String sdumLsmSparqlEndPoint = "";


	private static final String PROPERTIES_FILE = "openiot.properties";
	
	//==============Scheduler====================
	
	private static final String SCHEDULER_LSM_META_GRAPH = "scheduler.core.lsm.openiotMetaGraph";
	private static final String SCHEDULER_LSM_DATA_GRAPH = "scheduler.core.lsm.openiotDataGraph";
	private static final String SCHEDULER_LSM_FUNCTIONAL_GRAPH = "scheduler.core.lsm.openiotFunctionalGraph";
	private static final String SCHEDULER_LSM_USER_NAME="scheduler.core.lsm.access.username";
	private static final String SCHEDULER_LSM_PASSWORD="scheduler.core.lsm.access.password";

	

	//==============SD&UM====================
	private static final String SDUM_LSM_FUNCTIONAL_GRAPH = "scheduler.core.lsm.openiotFunctionalGraph";
	private static final String SDUM_LSM_SPARQL_END_POINT = "sdum.core.lsm.sparql.endpoint";
	
	
	
	private Properties props = null;
	

	public PropertyManagement() {
		initializeProperties();
	}
	
	/**
	 * Initialize the Properties
	 */
	private void initializeProperties() {

		String jbosServerConfigDir = System.getProperty("jboss.server.config.dir");
		String openIotConfigFile = jbosServerConfigDir + File.separator + PROPERTIES_FILE;
		props = new Properties();

		logger.debug("jbosServerConfigDir:" + openIotConfigFile);

		FileInputStream fis = null;

		try {
			fis = new FileInputStream(openIotConfigFile);

		} catch (FileNotFoundException e) {
			// TODO Handle exception

			logger.error("Unable to find file: " + openIotConfigFile);

		}

		// loading properites from properties file
		try {
			props.load(fis);
		} catch (IOException e) {
			// TODO Handle exception
			logger.error("Unable to load properties from file " + openIotConfigFile);
		}

	}
	
	
    public String getSchedulerLsmMetaGraph() {
		return props.getProperty(SCHEDULER_LSM_META_GRAPH);
	}

	public String getSchedulerLsmDataGraph() {
		return props.getProperty(SCHEDULER_LSM_DATA_GRAPH);
	}

	public String getSchedulerLsmFunctionalGraph() {
		return props.getProperty(SCHEDULER_LSM_FUNCTIONAL_GRAPH);
	}

	public String getSchedulerLsmUserName() {
		return props.getProperty(SCHEDULER_LSM_USER_NAME);
	}

	public String getSchedulerLsmPassword() {
		return props.getProperty(SCHEDULER_LSM_PASSWORD);
	}

	public String getSdumLsmFunctionalGraph() {
		return props.getProperty(SDUM_LSM_FUNCTIONAL_GRAPH);
	}

	public String getSdumLsmSparqlEndPoint() {
		return props.getProperty(SDUM_LSM_SPARQL_END_POINT);
	}



}
