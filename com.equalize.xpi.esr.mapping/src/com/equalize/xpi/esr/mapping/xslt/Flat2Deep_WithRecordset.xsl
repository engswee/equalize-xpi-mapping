<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" >
	<xsl:output encoding="UTF-8"/>

	<!-- At the root element level, manually select the child segments -->
	<xsl:template match="*[local-name() = 'MT_Deep']">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()"/>
		</xsl:copy>
	</xsl:template>	
	<xsl:template match="Recordset">
		<xsl:copy>
			<xsl:apply-templates select="Header"/>
			<xsl:apply-templates select="Delivery"/>
			<xsl:apply-templates select="Footer"/>
		</xsl:copy>
	</xsl:template>
	
	<!-- At the Delivery and Order level -->	
	<xsl:template match="Delivery">
		<!-- (1) - Save parent key value to be used to select corresponding child segments -->
		<xsl:variable name="deliveryno" select="DeliveryNo"/>
		<xsl:copy>
			<!-- (2) - Select the child elements that are fields -->
			<xsl:apply-templates select="*[not(*)]"/>
			<!-- (3) - Select the child segments which have matching value as parent key value -->
			<xsl:apply-templates select="../Order[DeliveryNo=$deliveryno]"/>
		</xsl:copy>
	</xsl:template>
	<xsl:template match="Order">
		<!-- (1) - Save parent key value to be used to select corresponding child segments -->
		<xsl:variable name="orderno" select="OrderNo"/>
		<xsl:copy>
			<!-- (2) - Select the child elements that are fields -->
			<xsl:apply-templates select="*[not(*)]"/>
			<!-- (3) - Select the child segments which have matching value as parent key value -->
			<xsl:apply-templates select="../OrderText[OrderNo=$orderno]"/>
			<xsl:apply-templates select="../Item[OrderNo=$orderno]"/>
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