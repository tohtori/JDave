#!/bin/sh
#
# Script for converting specdox generated XML documents to html.
# This script uses Saxon 8 but any tool which supports XSLT 2 should work.
#
# Usage: dox2html.sf </path/to/xml/files/>
#

if [ -z "$SAXON_HOME" ] ; then
  echo "Please set environment variable SAXON_HOME to point to saxon installation dir."
  exit 1
fi

if [ -z "$1" ] ; then
  echo "Please give path to directory where XML files are."
  exit 1
fi

PRGDIR=`dirname "$0"`

echo '<?xml version="1.0" ?><foo></foo>' | java -jar $SAXON_HOME/saxon8.jar - $PRGDIR/specdox.xsl spec-file-dir=$1
