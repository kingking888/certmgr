/*
 * Copyright (c) 2015-2016 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.certmgr.certs.x509;

import java.io.IOException;

import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;

import de.carne.certmgr.certs.asn1.ASN1Data;

/**
 * General name object.
 */
public abstract class GeneralName extends ASN1Data {

	private final GeneralNameType type;

	/**
	 * Construct {@code GeneralNameType}.
	 *
	 * @param type The name type.
	 */
	protected GeneralName(GeneralNameType type) {
		this.type = type;
	}

	/**
	 * Decode {@code GeneralName} object from an ASN.1 data object.
	 *
	 * @param primitive The ASN.1 data object to decode.
	 * @return The decoded name object.
	 * @throws IOException if an I/O error occurs during decoding.
	 */
	public static GeneralName decode(ASN1Primitive primitive) throws IOException {
		ASN1TaggedObject taggedObject = decodePrimitive(primitive, ASN1TaggedObject.class);
		int nameTypeTag = taggedObject.getTagNo();
		GeneralName name;

		switch (nameTypeTag) {
		case GeneralNameType.OTHER_NAME_TAG:
			name = null;
			break;
		case GeneralNameType.RFC822_NAME_TAG:
			name = StringName.genericDecode(GeneralNameType.RFC822_NAME, taggedObject.getObject());
			break;
		case GeneralNameType.DNS_NAME_TAG:
			name = StringName.genericDecode(GeneralNameType.DNS_NAME, taggedObject.getObject());
			break;
		case GeneralNameType.X400_ADDRESS_TAG:
			name = null;
			break;
		case GeneralNameType.DIRECTORY_NAME_TAG:
			name = StringName.genericDecode(GeneralNameType.DIRECTORY_NAME, taggedObject.getObject());
			break;
		case GeneralNameType.EDI_PARTY_NAME_TAG:
			name = null;
			break;
		case GeneralNameType.UNIFORM_RESOURCE_IDENTIFIER_TAG:
			name = StringName.genericDecode(GeneralNameType.UNIFORM_RESOURCE_IDENTIFIER, taggedObject.getObject());
			break;
		case GeneralNameType.IP_ADDRESS_TAG:
			name = StringName.genericDecode(GeneralNameType.IP_ADDRESS, taggedObject.getObject());
			break;
		case GeneralNameType.REGISTERED_ID_TAG:
			name = null;
			break;
		default:
			throw new IOException("Unsupported general name type: " + nameTypeTag);
		}
		return name;
	}

	/**
	 * Get this name object's type.
	 *
	 * @return This name object's type.
	 */
	public GeneralNameType getType() {
		return this.type;
	}

}
