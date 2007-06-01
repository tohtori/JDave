<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" indent="yes" />
	<xsl:param name="spec-file-dir" />
	<xsl:param name="output-dir">.</xsl:param>
	<xsl:param name="frameset-index-filename">index.html</xsl:param>
	<xsl:variable name="index-frame-filename">specifications-frame.html</xsl:variable>
	<xsl:variable name="content-frame-filename">specifications-contents.html</xsl:variable>

	<xsl:template match="/">
		<xsl:result-document href="{$output-dir}/{$index-frame-filename}">
			<xsl:call-template name="generate-frame-html">
				<xsl:with-param name="frame-type">index</xsl:with-param>
			</xsl:call-template>
		</xsl:result-document>
		<xsl:result-document href="{$output-dir}/{$content-frame-filename}">
			<xsl:call-template name="generate-frame-html">
				<xsl:with-param name="frame-type">content</xsl:with-param>
			</xsl:call-template>
		</xsl:result-document>
		<xsl:result-document href="{$output-dir}/{$frameset-index-filename}">
			<html>
				<frameset cols="20%,80%">
					<frame src="{$index-frame-filename}" name="indexFrame" />
					<frame src="{$content-frame-filename}" name="contentFrame" />
				</frameset>
			</html>
		</xsl:result-document>
	</xsl:template>

	<xsl:template name="generate-frame-html">
		<xsl:param name="frame-type" />
		<html>
			<body>
				<xsl:for-each select="collection(concat($spec-file-dir, '?select=*.xml'))">
					<xsl:sort select="//specification/@name" />
					<xsl:choose>
						<xsl:when test="$frame-type = 'index'">
							<xsl:apply-templates select="specification" mode="index" />
						</xsl:when>
						<xsl:otherwise>
							<xsl:apply-templates select="specification" mode="content" />
						</xsl:otherwise>
					</xsl:choose>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="specification" mode="index">
		<xsl:element name="a">
			<xsl:attribute name="href"><xsl:value-of select="$content-frame-filename" />#<xsl:value-of select="@name" /></xsl:attribute>
			<xsl:attribute name="target">contentFrame</xsl:attribute>
			<xsl:value-of select="@name" />
		</xsl:element>
		<br />
	</xsl:template>

	<xsl:template match="specification" mode="content">
		<xsl:element name="a">
			<xsl:attribute name="name"><xsl:value-of select="@name" /></xsl:attribute>
		</xsl:element>
		<h3><xsl:value-of select="@name" /></h3>
		<xsl:for-each select="contexts/context">
			<p><strong><xsl:value-of select="@name" /></strong></p>
			<ul>
				<xsl:for-each select="behaviors/behavior">
					<li><xsl:value-of select="@name" /></li>
				</xsl:for-each>
			</ul>
		</xsl:for-each>
		<hr />
	</xsl:template>
</xsl:stylesheet>