package org.openiot.lsm.server;
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
import java.util.Date;

import org.openiot.lsm.beans.Observation;
import org.openiot.lsm.beans.Sensor;
import org.openiot.lsm.schema.LSMSchema;





/**
 * @author root
 *
 */

public interface LSMServer {
	
	/**
	 * add new Sensor
	 *
	 */
	public String sensorAdd(Sensor sensor);
	public boolean sensorAdd(String triple,String graphURL);
	public boolean sensorDelete(String sensorURL,String graphURL);
	public Sensor getSensorById(String sensorURL,String graphURL);
	public Sensor getSensorBySource(String sensorsource,String graphURL);
	
	public boolean sensorDataUpdate(String triples,String graphURL);
	public boolean sensorDataUpdate(Observation observation);
	public boolean deleteAllReadings(String sensorURL,String graphURL);
	public boolean deleteAllReadings(String sensorURL, String graphURL,String dateOperator, Date fromTime, Date toTime);
	
	public boolean pushRDF(String graphURL,String triples);
	public boolean deleteTriples(String graphURL, String triples);
	public boolean deleteTriples(String graphURL);
	
	public void uploadSchema(LSMSchema schema,String name);
}
