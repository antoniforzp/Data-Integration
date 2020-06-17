<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>

    <xsl:template match="/">


        <xsl:for-each select="//movie/languages[language = 'English']">
            <xsl:sort select="../year" order="ascending"/>

            <xsl:value-of select="../title"/>
            <xsl:text>&#xa;</xsl:text>

            <xsl:text>box office: </xsl:text>
            <xsl:value-of select="../year"/>
            <xsl:text>&#xa;</xsl:text>

            <xsl:text>&#xa;</xsl:text>
        </xsl:for-each>


    </xsl:template>
</xsl:stylesheet>