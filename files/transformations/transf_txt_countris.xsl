<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="text"/>

    <!--Generate TXT file that shows the films of a given country.-->
    <xsl:template match="/">
        <directors>
            <xsl:for-each select="/movies/movie/country">
                <xsl:variable name="currCountry" select="current()"/>

                <xsl:element name="director">
                    <xsl:attribute name="name">
                        <xsl:value-of select="current()"/>
                    </xsl:attribute>

                    <xsl:for-each select="//movie[director = $directorName]/title">

                        <movie>
                            <xsl:value-of select="current()"/>
                        </movie>

                    </xsl:for-each>

                </xsl:element>

            </xsl:for-each>
        </directors>
    </xsl:template>
</xsl:stylesheet>


