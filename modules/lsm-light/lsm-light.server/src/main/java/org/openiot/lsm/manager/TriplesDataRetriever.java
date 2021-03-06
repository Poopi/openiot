package org.openiot.lsm.manager;

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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.openiot.lsm.beans.Place;
import org.openiot.lsm.beans.Sensor;
import org.openiot.lsm.beans.User;
import org.openiot.lsm.http.ObjectServlet;
import org.openiot.lsm.utils.ConstantsUtil;
import org.openiot.lsm.utils.DateUtil;
import org.openiot.lsm.utils.VirtuosoConstantUtil;
import org.openiot.lsm.utils.XSLTMapFile;


import net.sf.saxon.*;


public class TriplesDataRetriever {

	public static String getTripleDataHasUnit(String dataType,String name,String value,String unit,String observationId,String observedURL,Date time){
		String triples = "";
		long id = System.nanoTime();
		triples+="<http://lsm.deri.ie/resource/"+id+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+dataType+">.\n"+ 
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#isObservedPropertyOf> <http://lsm.deri.ie/resource/"+observationId+">.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#value> \""+value+"\"^^<http://www.w3.org/2001/XMLSchema#double>.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#unit> \""+unit+"\".\n"+
//				"<http://lsm.deri.ie/resource/"+id+"> <http://www.w3.org/2000/01/rdf-schema#label> \""+name+"\".\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://purl.oclc.org/NET/ssnx/ssn#observedProperty> <"+observedURL+">.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://purl.oclc.org/NET/ssnx/ssn#observationResultTime> \""+DateUtil.date2StandardString(time)+"\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\n";
		return triples;
	}
	
	
	public static String getTripleDataHasNoUnit(String dataType,String name,String value,String observationId,String observedURL,Date time){
		String triples = "";
		long id = System.nanoTime();
		triples+=
				"<http://lsm.deri.ie/resource/"+id+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+dataType+">.\n"+ 
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#isObservedPropertyOf> <http://lsm.deri.ie/resource/"+observationId+">.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#value> \""+value+"\".\n"+
//				"<http://lsm.deri.ie/resource/"+id+"> <http://www.w3.org/2000/01/rdf-schema#label> \""+name+"\".\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://purl.oclc.org/NET/ssnx/ssn#observedProperty> <"+observedURL+">.\n"+
				"<"+observedURL+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://purl.oclc.org/NET/ssnx/ssn#Property>.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://purl.oclc.org/NET/ssnx/ssn#observationResultTime> \""+DateUtil.date2StandardString(time)+"\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\n";
		return triples;
	}
	
	public static String getObservationTripleData(String obsId,String sensorId,String featureOfInterest,Date time){
		String triples = "";		
		triples+="<http://lsm.deri.ie/resource/"+obsId+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://purl.oclc.org/NET/ssnx/ssn#Observation>.\n"+ 
				"<http://lsm.deri.ie/resource/"+obsId+"> <http://purl.oclc.org/NET/ssnx/ssn#observedBy> <"+sensorId+">.\n"+				
				"<http://lsm.deri.ie/resource/"+obsId+"> <http://purl.oclc.org/NET/ssnx/ssn#featureOfInterest> <"+featureOfInterest+">.\n"+
				"<http://lsm.deri.ie/resource/"+obsId+"> <http://purl.oclc.org/NET/ssnx/ssn#observationResultTime> \""+DateUtil.date2StandardString(time)+"\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\n";
		return triples;
	}
	
	public static String getTripleComplexTypeData(String dataType,String name,String value,String unit,String observationId,String observedURL,Date time){
		String triples = "";
		long id = System.nanoTime();
		triples+="<http://lsm.deri.ie/resource/"+id+"> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <"+dataType+">.\n"+ 
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#isObservedPropertyOf> <http://lsm.deri.ie/resource/"+observationId+">.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#value> \""+value+"\"^^<http://www.w3.org/2001/XMLSchema#string>.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://lsm.deri.ie/ont/lsm.owl#unit> \""+unit+"\"^^<http://www.w3.org/2001/XMLSchema#string>.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://www.w3.org/2000/01/rdf-schema#label> \""+name+"\".\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://purl.oclc.org/NET/ssnx/ssn#observedProperty> <"+observedURL+">.\n"+
				"<http://lsm.deri.ie/resource/"+id+"> <http://purl.oclc.org/NET/ssnx/ssn#observationResultTime> \""+DateUtil.date2StandardString(time)+"\"^^<http://www.w3.org/2001/XMLSchema#dateTime>.\n";
		return triples;
	}
	
	public static String getSensorTripleMetadata(Sensor s,String sensorTypeId){
		String triples = "";
		String xsltPath = XSLTMapFile.sensormeta2xslt;
		xsltPath = ConstantsUtil.realPath + xsltPath;
//		xsltPath = "webapp/WEB-INF" + xsltPath;
		TransformerFactory tFactory = TransformerFactory.newInstance();
        String xml = "";
        try {
        	Place place = s.getPlace();
        	String foi = VirtuosoConstantUtil.sensorObjectDataPrefix + 
					Double.toString(place.getLat()).replace(".", "").replace("-", "")+
					Double.toString(place.getLng()).replace(".", "").replace("-", "");
        	
            Transformer transformer = tFactory.newTransformer(new StreamSource(new File(xsltPath)));
            transformer.setParameter("sensorId", s.getId());
            transformer.setParameter("sourceType", s.getSourceType());
//            transformer.setParameter("sensortype", s.getSensorType());
            transformer.setParameter("sourceURL", s.getSource());
            transformer.setParameter("placeId", place.getId());
            transformer.setParameter("geonameId", place.getGeonameid());
            transformer.setParameter("city", place.getCity());
            transformer.setParameter("province", place.getProvince());
            transformer.setParameter("country", place.getCountry());
            transformer.setParameter("continent", place.getContinent());
            transformer.setParameter("lat", place.getLat());
            transformer.setParameter("lng", place.getLng());
            transformer.setParameter("foi", foi);
            transformer.setParameter("name", s.getName());
            transformer.setParameter("userId",s.getUser().getId());
          
            xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><root></root>";          
            xml = xml.trim().replaceFirst("^([\\W]+)<","<");
            
            InputStream inputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
            Writer outWriter = new StringWriter();  
            StreamResult result = new StreamResult( outWriter );            
            transformer.transform(new StreamSource(inputStream),result);
            triples = outWriter.toString().trim();       
            
            String sensorTypeTriples = "";
            String typeId = "";
            if(sensorTypeId=="")
            	typeId = "http://lsm.deri.ie/resource/"+System.nanoTime();
        	sensorTypeTriples="\n<" + s.getId() + "> <http://lsm.deri.ie/ont/lsm.owl#hasSensorType> <" + typeId +">.\n"
        		+"<" + typeId + "> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://lsm.deri.ie/ont/lsm.owl#SensorType>.\n"
        		+"<" + typeId + "> <http://www.w3.org/2000/01/rdf-schema#label> \"" + s.getSensorType()+"\".\n";
        	triples+=sensorTypeTriples;
            
            String observesTriples = "";           
            for(String classURL:s.getProperties().keySet()){
            	String instanceId = "http://lsm.deri.ie/resource/"+System.nanoTime();
            	s.getProperties().put(classURL, instanceId);
            	observesTriples+= "\n<" + s.getId() + "> <http://purl.oclc.org/NET/ssnx/ssn#observes> <" + instanceId +">.\n";
            	observesTriples+= "<" + instanceId + "> <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <" + classURL + ">.\n";
            	observesTriples+= "<" + instanceId + "> <http://purl.oclc.org/NET/ssnx/ssn#isPropertyOf> <" + foi + ">.\n";
            }
            
            triples+=observesTriples;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return triples;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("javax.xml.transform.TransformerFactory",
                "net.sf.saxon.TransformerFactoryImpl");
		User user = new User();
		user.setId("dafasdf");
		Sensor sensor  = new Sensor();
        sensor.setId("http://lsm.deri.ie/resource/8a82919d3264f4ac013264f4e14501c0");
        sensor.setName("hello");
        sensor.setAuthor("admin");
//		sensor.setSensorType("bikehire");
		sensor.setSourceType("sdfg");
		sensor.setInfor("asfdfs");
		sensor.setSource("affag");
//		sensor.setUser(user);
		sensor.setTimes(new Date());
        Place place = new Place();
        place.setLat(32325);
        place.setLng(324);
        sensor.setPlace(place);
        System.out.println(TriplesDataRetriever.getSensorTripleMetadata(sensor,""));
	}
}
