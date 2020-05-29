<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

    <!--Generate XML file that shows the listing of the films of a given director-->
    <xsl:template match="/">
        <html>
            <body>
                <h2 style="text-align: center">Movies</h2>
                <table border="1" style="margin-left:auto; margin-right:auto;">
                    <tr>
                        <th>Poster</th>
                        <th>Title</th>
                        <th>Director</th>
                    </tr>
                    <xsl:for-each select="movie">
                        <xsl:sort select="title"/>
                        <tr>
                            <td>
                                <img>
                                    <xsl:attribute name="src">
                                        <xsl:value-of select="cover"/>
                                    </xsl:attribute>
                                </img>
                            </td>
                            <td>
                                <xsl:value-of select="title"/>
                            </td>
                            <td>
                                <xsl:value-of select="director"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>


