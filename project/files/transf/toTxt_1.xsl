<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>

    <xsl:template match="/">


        <xsl:variable name="root" select="movies"/>
        <xsl:for-each select="distinct-values(//movie/productionCountries/country)">

            <xsl:value-of select="current()"/>
            <xsl:text>&#xa;</xsl:text>

            <xsl:for-each select="$root/movie/productionCountries[country = current()]">
                <xsl:text>&#x9;</xsl:text>
                <xsl:text>-</xsl:text>
                <xsl:value-of select="current()/../title"/>
                <xsl:text>&#xa;</xsl:text>

            </xsl:for-each>

            <xsl:text>&#xa;</xsl:text>

        </xsl:for-each>


    </xsl:template>
</xsl:stylesheet>


