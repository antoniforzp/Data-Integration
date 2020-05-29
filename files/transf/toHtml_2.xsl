<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>
    <xsl:template match="/movies">
        <html>
            <body>
                <h2 style="text-align: center">Top grossing movies</h2>
                <table border="1" style="margin-left:auto; margin-right:auto;">
                    <tr>
                        <th>Title</th>
                        <th>Box office</th>
                    </tr>
                    <xsl:for-each select="movie">
                        <xsl:sort select="boxOffice" data-type="number" order="descending"/>
                        <tr>
                            <td>
                                <xsl:value-of select="title"/>
                            </td>
                            <td>
                                <xsl:value-of select="boxOffice"/>$
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>

