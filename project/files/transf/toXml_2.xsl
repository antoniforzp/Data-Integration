<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <xsl:template match="/">

        <movies>
            <xsl:for-each select="//movie">
                <xsl:sort select="count(cast/actor)" order="descending"/>
                <movie>
                    <title>
                        <xsl:value-of select="title"/>
                    </title>

                    <xsl:element name="actors">
                        <xsl:attribute name="quantity">
                            <xsl:value-of select="count(cast/actor)"/>
                        </xsl:attribute>

                        <xsl:for-each select="cast/actor">
                            <actor>
                                <xsl:value-of select="current()"/>
                            </actor>
                        </xsl:for-each>
                    </xsl:element>

                </movie>
            </xsl:for-each>
        </movies>

    </xsl:template>
</xsl:stylesheet>


