<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:template match="/">

        <directors>
            <xsl:variable name="root" select="movies"/>

            <xsl:for-each select="distinct-values(//movie/director)">

                <xsl:element name="director">
                    <xsl:attribute name="name">
                        <xsl:value-of select="current()"/>
                    </xsl:attribute>

                    <xsl:for-each select="$root/movie[director = current()]/title">
                        <movie>
                            <xsl:value-of select="current()"/>
                        </movie>
                    </xsl:for-each>
                </xsl:element>

            </xsl:for-each>

        </directors>

    </xsl:template>

</xsl:stylesheet>


