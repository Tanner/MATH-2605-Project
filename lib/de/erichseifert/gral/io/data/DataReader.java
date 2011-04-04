/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2011 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <michael.seifert[at]gmx.net>
 *
 * This file is part of GRAL.
 *
 * GRAL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GRAL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GRAL.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.erichseifert.gral.io.data;

import java.io.IOException;
import java.io.InputStream;

import de.erichseifert.gral.data.DataSource;


/**
 * Interface that provides a function to retrieve a data source.
 */
public interface DataReader {
	/**
	 * Returns a DataSource that was imported.
	 * @param input Input to be read.
	 * @param types Number types for the columns of the DataSource.
	 * @return DataSource Imported data.
	 * @throws IOException when the file format is not valid or when
	 *         experiencing an error during file operations.
	 */
	DataSource read(InputStream input, Class<? extends Number>... types)
		throws IOException;

	/**
	 * Returns the setting for the specified key.
	 * @param <T> return type
	 * @param key key of the setting
	 * @return the value of the setting
	 */
	<T> T getSetting(String key);

	/**
	 * Sets the setting for the specified key.
	 * @param <T> value type
	 * @param key key of the setting
	 * @param value value of the setting
	 */
	<T> void setSetting(String key, T value);

}
