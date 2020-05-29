<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="xml"/>

    <xsl:template match="/">

        <xsl:for-each select="//movie/title">
            <xsl:value-of select="current()"/>
        </xsl:for-each>

    </xsl:template>


</xsl:stylesheet>