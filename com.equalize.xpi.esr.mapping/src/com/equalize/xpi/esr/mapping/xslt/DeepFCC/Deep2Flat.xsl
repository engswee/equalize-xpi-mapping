<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output encoding="UTF-8"/>

	<!-- *[*]      = any element which has child elements, i.e. any segments with child segments/fields -->
	<!-- *[not(*)] = any element with no child elements, i.e. fields -->	
	
	<!-- Match any segment with child segments/fields -->
	<xsl:template match="*[*]">
		<!-- (1) - Copy the current segment, then select attributes and child elements that are fields -->
		<xsl:copy>
			<xsl:apply-templates select="@* | *[not(*)]"/> 
		</xsl:copy>
		<!-- (2) - Further select child elements that are segments -->
		<xsl:apply-templates select="*[*]"/>
	</xsl:template>
	
	<!-- Match root element, copy details and and select child attributes and nodes -->
	<xsl:template match="*[local-name()='MT_Deep']">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>
	
	<!-- Match all other attributes (@*) and other node types (elements, comments) -->
	<!-- Copy the current element, and select child attributes and nodes -->
	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>	