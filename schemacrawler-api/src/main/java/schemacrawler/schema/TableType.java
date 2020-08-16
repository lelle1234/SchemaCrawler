/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2020, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/
package schemacrawler.schema;


import static us.fatehi.utility.Utility.isBlank;
import static us.fatehi.utility.Utility.requireNotBlank;

import java.io.Serializable;

/**
 * Represents a type of table in the database. Examples could be a base table, a
 * view, a global temporary table, and so on. The table type name is
 * case-sensitive, as it is known to the database system. However, string
 * comparisons are case-insensitive.
 */
public final class TableType
  implements Serializable, Comparable<TableType>
{

  private static final long serialVersionUID = -8172248482959041873L;
  public static final TableType UNKNOWN = new TableType("unknown");
  private final String tableType;

  /**
   * Constructor for table type. Table type is case-sensitive.
   */
  public TableType(final String tableTypeString)
  {
    requireNotBlank(tableTypeString, "No table type provided");
    tableType = tableTypeString.trim();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(final TableType other)
  {
    if (other == null)
    {
      return 1;
    }

    final String thisToString = toString();
    final String otherToString = other.toString();

    if (thisToString.equalsIgnoreCase(otherToString))
    {
      return 0;
    }

    // Sort tables first
    final boolean isThisTable = "TABLE".equalsIgnoreCase(thisToString);
    final boolean isOtherTable = "TABLE".equalsIgnoreCase(otherToString);
    if (isThisTable && !isOtherTable)
    {
      return -1;
    }
    else if (!isThisTable && isOtherTable)
    {
      return 1;
    }

    // Sort views second
    final boolean isThisView = "VIEW".equalsIgnoreCase(thisToString);
    final boolean isOtherView = "VIEW".equalsIgnoreCase(otherToString);
    if (isThisView && !isOtherView)
    {
      return -1;
    }
    else if (!isThisView && isOtherView)
    {
      return 1;
    }

    return thisToString.compareTo(otherToString);
  }

  /**
   * The table type, with the case preserved.
   *
   * @return The table type
   */
  public String getTableType()
  {
    return tableType;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + (tableType == null?
                               0:
                               tableType
                                 .toLowerCase()
                                 .hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    if (obj == null)
    {
      return false;
    }
    if (!(obj instanceof TableType))
    {
      return false;
    }
    final TableType other = (TableType) obj;
    if (tableType == null)
    {
      return other.tableType == null;
    }
    else
    { return tableType.equalsIgnoreCase(other.tableType); }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString()
  {
    return tableType.toLowerCase();
  }

  /**
   * Checks if a string is equal to this table type. This is a case-insensitive
   * check.
   *
   * @return True if the string is the same as this table type
   */
  public boolean isEqualTo(final String testTableType)
  {
    if (isBlank(testTableType))
    {
      return false;
    }
    return tableType.equalsIgnoreCase(testTableType.trim());
  }

  /**
   * Checks if the table type is a view of any kind.
   */
  public boolean isView()
  {
    return tableType != null && (tableType
                                   .toUpperCase()
                                   .contains("VIEW") || tableType
                                   .toUpperCase()
                                   .contains("MATERIALIZED"));
  }

}
