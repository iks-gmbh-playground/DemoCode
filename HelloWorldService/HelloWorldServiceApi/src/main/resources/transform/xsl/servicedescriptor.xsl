<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xsl:stylesheet 
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
 xmlns="http://www.w3.org/1999/xhtml" 
 xmlns:html="http://www.w3.org/1999/xhtml" 
 xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
 xmlns:ppp="http://titanium.dstc.edu.au/xml/xs3p"
 xmlns:desc="http://www.dhl.com/servicedescriptor/1.0" 
 version="1.0" 
 exclude-result-prefixes="xsd ppp html">

   <xsl:output 
    method="xml" 
    encoding="UTF-8"
    standalone="yes"
    version="1.0"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" 
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" 
    indent="yes"/>


   <!-- ******** Global Parameters ******** -->

   <!-- Filename of the contract xsd -->
   <xsl:param name="contractFile">contract.html</xsl:param>

   <!--
     Main template that starts the process
     -->
   <xsl:template match="desc:servicedescriptor">

      <html>
         <head>
            <!-- Set title bar -->
            <title><xsl:value-of select="desc:general/desc:display-name"/></title>

            <!-- Set CSS styles -->
            <style type="text/css">
              body{
                font-family:verdana;
                background-color:whitesmoke;
                font-size:small;
              }
              table{
                }
              th{
                background-color:steelblue;
                color:white;
                text-align:left;
              }
              td,th{
                padding:5px;
              }
              h2{
                width:100%;
                background-color:lightgrey;
              }
              .label{
                width:200px;
                background-color:lightgray;
              }
            </style>

         </head>
         <body>
            <!-- Title -->
            <h1><a name="top"><xsl:value-of select="desc:general/desc:display-name"/></a></h1>
            <hr/>
            <h2>Common</h2>
            <table>   
              <tr><td class="label">Id</td><td><xsl:value-of select="desc:general/desc:id"/></td></tr>
              <tr><td class="label">Purpose</td><td><xsl:value-of select="desc:general/desc:purpose"/></td></tr>
              <tr><td class="label">Version</td><td><xsl:value-of select="desc:general/desc:version"/></td></tr>
              <tr><td class="label">Status</td><td><xsl:value-of select="desc:general/desc:status"/></td></tr>
              <tr><td class="label">Active</td><td><xsl:value-of select="desc:general/desc:active-since"/> - <xsl:value-of select="desc:general/desc:active-until"/></td></tr>
              <tr><td class="label">Contact</td><td>
              <xsl:for-each select="desc:general/desc:contacts/desc:contact">
             	 <xsl:value-of select="./desc:name"/> (<xsl:value-of select="./desc:email"/>)<br/>
           		</xsl:for-each>   
              </td></tr>
            </table>
                        
            <h2>Classifications</h2>
            <table>   
              <tr>
                <th>Type</th>
                <th>Class</th>
              </tr>         
              <xsl:for-each select="desc:classifications/desc:classification">
                <tr>
                  <td class="label"><xsl:value-of select="@type"/></td>
                  <td><xsl:value-of select="@value"/></td>
                </tr>
              </xsl:for-each>
            </table>

 			<h2>Consumers (Services, applications, etc.)</h2>
            <table>   
              <tr>
                <th>Name</th>                
              </tr>         
              <xsl:for-each select="desc:clients/desc:client">
                <tr>
                  <td class="name"><xsl:value-of select="@name"/></td>                  
                </tr>
              </xsl:for-each>
            </table>
            
            <h2>Request interface</h2>
            <table>   
              <tr>
                <th>Operation</th>
                <th>Input</th>
                <th>Output</th>
                <th>Error</th>
                <th>Description</th>
              </tr>         
              <xsl:for-each select="desc:operations/desc:operation">
                <xsl:variable name="href"><xsl:value-of select="$contractFile"/></xsl:variable>
                <tr>
                  <td class="label"><xsl:value-of select="@name"/></td>
                  <td><a href="{$href}"><xsl:value-of select="desc:in/@ref"/></a></td>
                  <td><a href="{$href}"><xsl:value-of select="desc:out/@ref"/></a></td>
                  <td><a href="{$href}"><xsl:value-of select="desc:error/@ref"/></a></td>
                  <td><xsl:value-of select="desc:purpose"/></td>
                </tr>
              </xsl:for-each>
            </table>

            <!-- h2>REST-Operationen</h2>
            <table>   
              <tr>
                <th>URI</th>
                <th>Operation</th>
                <th>HTTP-Method</th>
                <th>Accept</th>
                <th>Content-Type</th>
                <th>Zweck</th>
              </tr>         
              <xsl:for-each select="desc:rest-uris/desc:rest-uri">                
                <tr>
                  <td class="label"><xsl:value-of select="@name"/></td>
                  <td><xsl:value-of select="@mapped_to"/></td>
                  <td><xsl:value-of select="@http-method"/></td>
                  <td><xsl:value-of select="@accept"/></td>
                  <td><xsl:value-of select="@content-type"/></td>
                  <td><xsl:value-of select="@purpose"/></td>
                </tr>
                <xsl:if test="current()/* [@name != '1']"> 
                <tr>
                  <td colspan="6" text-align="center">
                      <table>
                        <tr>
			                <th>Query Parameter</th>
			                <th>required</th>
			                <th>Beschreibung</th>
                		</tr>
		                 <xsl:for-each select="./desc:uri-parameter">
		                 	<tr>
		                	  <td class="label"><xsl:value-of select="@name"/></td>
		                	  <td><xsl:value-of select="@required"/></td>
		                	  <td><xsl:value-of select="@purpose"/></td>
							</tr> 					              	  
		                 </xsl:for-each>	                     
	                 </table>  
	                 </td>
	             </tr>       
                 </xsl:if>
              </xsl:for-each>
            </table -->
            
            <h2>Bindings</h2>
            <xsl:for-each select="desc:bindings">
              <h3><xsl:value-of select="@stage"/></h3>
              <table>   
                <tr>
                  <th>Name</th>
                  <th>Contract</th>
                  <th>Endpoint</th>
                </tr>         
                <xsl:for-each select="desc:binding">
                  <xsl:variable name="href"><xsl:value-of select="desc:contract"/></xsl:variable>
                  <tr>
                    <td class="label"><xsl:value-of select="@name"/></td>
                    <td><a href="{$href}" target="_blank"><xsl:value-of select="desc:contract"/></a></td>
                    <td><a href="#"><xsl:value-of select="desc:endpoint"/></a></td>
                  </tr>
                </xsl:for-each>
              </table>
            </xsl:for-each>

            <h2>Quality characteristics</h2>
            <xsl:for-each select="desc:quality-of-service/desc:operation">
              <h3><xsl:value-of select="@name-ref"/></h3>
              <xsl:for-each select="desc:property">
                <h4><xsl:value-of select="@name"/> (<xsl:value-of select="desc:stage/@ref"/>)</h4>
                <table>   
                  <tr>
                    <th>Binding</th>
                    <th>Value</th>
                  </tr>         
                  <xsl:for-each select="desc:stage/desc:qos">
                    <tr>
                      <td class="label"><xsl:value-of select="@binding-ref"/></td>
                      <td><xsl:value-of select="@value"/></td>
                    </tr>
                  </xsl:for-each>
                </table>
              </xsl:for-each>
            </xsl:for-each>
        </body>
      </html>
   </xsl:template>
</xsl:stylesheet>
