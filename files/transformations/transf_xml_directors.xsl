<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <!--    Generate XML file that shows the listing of the films of a given director-->
    <xsl:template match="/">
        <directors>
            <xsl:for-each select="/movies/movie[not(. = preceding::type/)]">
                <xsl:variable name="directorName" select="current()"/>

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


