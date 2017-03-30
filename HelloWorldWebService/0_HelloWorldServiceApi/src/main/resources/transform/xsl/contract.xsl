<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xsl:stylesheet 
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
 xmlns="http://www.w3.org/1999/xhtml" 
 xmlns:html="http://www.w3.org/1999/xhtml" 
 xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
 xmlns:desc="http://www.dhl.com/servicedescriptor/1.0" 
 version="1.0">

   <xsl:output 
    method="xml" 
    encoding="UTF-8"
    standalone="yes"
    version="1.0"
    doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" 
    doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" 
    indent="yes"/>


   <!-- ******** Global Parameters ******** -->

   <xsl:param name="title">Service Contract</xsl:param>

   <xsl:template match="/xsd:schema">
      <html>
         <head>
            <!-- Set title bar -->
            <title>Service Contract</title>

            <!-- Set CSS styles -->
            <style type="text/css">
              body{
                font-family:verdana;
                background-color:white;
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
                padding:10px;
              }
              .comment{
                padding:5px;
                margin-left:0px;
                display:inline-block;
                border-bottom: 1px solid gray;
              }
              .label{
                width:400px;
                background-color:steelblue;
                color:white;
                margin-right:0px;
                padding:5px;
                border-bottom: 1px solid gray;
                display:inline-block;
                font-weight:bold;
              }
              .type{
                font-weight:bold;
                color:white;
                padding:5px;
                width:10px;
                text-align:center;
                display:inline-block;
                border-bottom: 1px solid gray;
              }
              .block{
                background-color:whitesmoke;
              }
              .response{
                background-color:gray;
              }
              .request{
                background-color:green;
              }
              .error{
                background-color:salmon;
              }
            </style>
         </head>
         <body>
            <h2>Messages</h2>
            <xsl:apply-templates select="xsd:element" mode="messages"/>
            <h2>Types</h2>
            <xsl:apply-templates select="xsd:complexType" mode="types"/>
            <h2>Lists and formats</h2>
            <xsl:apply-templates select="xsd:simpleType" mode="enums"/>
            <!--<h2>Imports</h2>
            <xsl:apply-templates select="xsd:import" mode="imports"/>-->
        </body>
      </html>
   </xsl:template>
   
   <!-- Messages -->
   <xsl:template match="*[contains(@name, 'Request') or contains(@name, 'Response') or contains(@name, 'Error')]" mode="messages">
     <div class="block">

       <xsl:variable name="operation">
        <xsl:choose>
          <xsl:when test="contains(@name, 'Request')">
            <xsl:variable name="valueLength" select="string-length(@name)-7"/>
            <xsl:value-of select="concat(substring(@name,1,$valueLength),' (Request)')"/>
          </xsl:when>
          <xsl:when test="contains(@name, 'Response')">
            <xsl:variable name="valueLength" select="string-length(@name)-8"/>
            <xsl:value-of select="concat(substring(@name,1,$valueLength),' (Response)')"/>
          </xsl:when>
          <xsl:otherwise>
            <xsl:value-of select='@name' />
          </xsl:otherwise>
        </xsl:choose>
       </xsl:variable>

       <!-- Operation type -->
       <xsl:if test="contains(@name, 'Request')">
         <div class="label request" title="Request"><xsl:value-of select="$operation"/></div>
       </xsl:if>
       <xsl:if test="contains(@name, 'Response')">
         <div class="label response" title="Response"><xsl:value-of select="$operation"/></div>
       </xsl:if>
       <xsl:if test="contains(@name, 'Error')">
         <div class="label error" title="Error"><xsl:value-of select="$operation"/></div>
       </xsl:if>
 
       <!-- Parameters -->
       <div class="comment"><xsl:value-of select="xsd:annotation/xsd:documentation"/></div>
       <br/>
       <table>
         <xsl:for-each select="xsd:complexType//xsd:sequence/xsd:element">
          <xsl:variable name="href"><xsl:value-of select="@type"/></xsl:variable>         
           <tr>
            <td><b><xsl:value-of select="@name"/></b></td>
            <td><xsl:value-of select="xsd:annotation/xsd:documentation"/></td>
            <td>(<a href="#{$href}"><xsl:value-of select="@type"/></a>)</td>
           </tr> 
         </xsl:for-each>
       </table>
     </div>
     <br/>
   </xsl:template>

   <!-- Enumerations -->
   <xsl:template match="*[local-name()='simpleType']" mode="enums">
     <div class="block">
       <a name="tns:{@name}"/><b class="label"><xsl:value-of select="@name"/></b>
       <div class="comment"><xsl:value-of select="xsd:annotation/xsd:documentation"/></div>
       <br/>
       <table>
         <xsl:for-each select="xsd:restriction/xsd:enumeration">
           <tr>
            <td><b><xsl:value-of select="@value"/></b></td>
            <td><xsl:value-of select="xsd:annotation/xsd:documentation"/></td>
           </tr> 
         </xsl:for-each>
       </table>
     </div>  
     <br/>
   </xsl:template>

   <!-- Types -->
   <xsl:template match="*[local-name()='complexType']" mode="types">
     <div class="block">
       <a name="tns:{@name}"/><b class="label"><xsl:value-of select="@name"/></b>
       <div class="comment"><xsl:value-of select="xsd:annotation/xsd:documentation"/></div>
       <br/>
       <table>
         <xsl:for-each select=".//xsd:sequence/xsd:element">
          <xsl:variable name="href"><xsl:value-of select="@type"/></xsl:variable>         
           <tr>
            <td><b><xsl:value-of select="@name"/></b></td>
            <td><xsl:value-of select="xsd:annotation/xsd:documentation"/></td>
            <td>(<a href="#{$href}"><xsl:value-of select="@type"/></a>)</td>
           </tr> 
         </xsl:for-each>
       </table>
     </div>
     <br/>
   </xsl:template>

   <!-- Types -->
   <xsl:template match="*[local-name()='import']" mode="imports">
     <div class="block">
       <xsl:variable name="href"><xsl:value-of select="@schemaLocation"/></xsl:variable>      
       <a href="{$href}"><xsl:value-of select="@namespace"/></a>
     </div>
     <br/>
   </xsl:template>

</xsl:stylesheet>
