<?xml version="1.0"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output indent="yes" method="text"/>
    <xsl:key name="item-by-value" match="director" use="."/>

    <xsl:template match="/">
        <xsl:apply-templates select="/movies/movie/director"/>
    </xsl:template>

    <xsl:template match="director">
        <xsl:if test="generate-id() = generate-id(key('item-by-value', normalize-space(.)))">
            <xsl:value-of select="current()"/>
            <xsl:text>&#xa;</xsl:text>

            <xsl:for-each select="//movie[director=current()]/title">
                <xsl:text>&#x9;</xsl:text>
                <xsl:value-of select="current()"/>
                <xsl:text>&#xa;</xsl:text>
            </xsl:for-each>
            <xsl:text>&#xa;</xsl:text>

        </xsl:if>
    </xsl:template>

    <xsl:template match="text()">
        <xsl:apply-templates/>
    </xsl:template>

</xsl:stylesheet>