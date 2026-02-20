<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/employees">
        <html>
            <head>
                <title>Employee List</title>
                <style>
                    table {
                        width: 100%;
                        border-collapse: collapse;
                    }
                    table, th, td {
                        border: 1px solid black;
                    }
                    th, td {
                        padding: 8px;
                        text-align: left;
                    }
                    th {
                        background-color: #f2f2f2;
                    }
                    .high-salary {
                        background-color: #90EE90; /* Green for high salary */
                    }
                    .medium-salary {
                        background-color: #FFFF99; /* Yellow for medium salary */
                    }
                    .low-salary {
                        background-color: #FFCCCB; /* Red for low salary */
                    }
                </style>
            </head>
            <body>
                <h2>Employee Details</h2>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Designation</th>
                            <th>Department</th>
                            <th>Salary</th>
                            <th>Date of Joining</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="employee">
                            <xsl:sort select="dateOfJoining" order="ascending" />
                            <tr>
                                <td><xsl:value-of select="id" /></td>
                                <td><xsl:value-of select="name" /></td>
                                <td><xsl:value-of select="position" /></td>
                                <td><xsl:value-of select="department" /></td>
                                
                                <td>
                                    <xsl:choose>
                                        <xsl:when test="number(salary) &gt;= 80000">
                                            <span class="high-salary"><xsl:value-of select="salary" /></span>
                                        </xsl:when>
                                        <xsl:when test="number(salary) &gt;= 60000">
                                            <span class="medium-salary"><xsl:value-of select="salary" /></span>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <span class="low-salary"><xsl:value-of select="salary" /></span>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                
                                <td><xsl:value-of select="dateOfJoining" /></td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
