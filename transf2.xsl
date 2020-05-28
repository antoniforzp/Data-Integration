<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:template match="movies">
        <directors>
            <xsl:for-each select="/movies/movie/director">
                <director>
                    <xsl:attribute name="name">
                        <xsl:value-of select="current()"/>
                    </xsl:attribute>
<!--                    <xsl:choose>-->
<!--                        <xsl:when test="/movies/movie/director=current()">-->
<!--                            <movie>-->
<!--                                <xsl:value-of select="../title"/>-->
<!--                            </movie>-->
<!--                        </xsl:when>-->
<!--                    </xsl:choose>-->
                </director>
            </xsl:for-each>
        </directors>
    </xsl:template>
</xsl:stylesheet>


