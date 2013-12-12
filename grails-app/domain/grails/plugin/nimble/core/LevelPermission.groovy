/*
 *  Nimble, an extensive application base for Grails
 *  Copyright (C) 2010 Bradley Beddoes
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package grails.plugin.nimble.core

import grails.util.Holders as ConfigurationHolder

/**
 * Represents a WildcardPermission in the data repository.
 * Each level of a wildcard permission (upto 6) is able to be represented by
 * this object. Support for auto creation of targets and populating of levels from UI
 *
 * @author Bradley Beddoes
 */
class LevelPermission extends Permission {

	private static final String tokenSep = ","
	private static final String levelSep = ":"

	static hasMany = [
		first: String,
		second: String,
		third: String,
		fourth: String,
		fifth: String,
		sixth: String
	]

	static mapping = {
		cache usage: 'read-write', include: 'all'
		table ConfigurationHolder.config.nimble.tablenames.levelpermission
	}

	static constraints = {
		first(minSize: 1)
		second(nullable: true)
		third(nullable: true)
		fourth(nullable: true)
		fifth(nullable: true)
		sixth(nullable: true)
	}

	LevelPermission() {
		type = Permission.defaultPerm
	}

	void buildTarget() {
		StringBuilder target = new StringBuilder()

		if (first) {
			addTokens first, target, false
			if (second) {
				addTokens second, target
				if (third) {
					addTokens third, target
					if (fourth) {
						addTokens fourth, target
						if (fifth) {
							addTokens fifth, target
							if (sixth) {
								addTokens sixth, target
							}
						}
					}
				}
			}
		}

		// Sanitize ordering so things are easy for human consumption more then anything
		this.target = target.toString().split(':').collect{ it.split(',').sort().join(',') }.join(':')
	}

	private void addTokens(o, StringBuilder target, boolean addLevelSep = true) {
		if (addLevelSep) {
			target.append levelSep
		}

		int last = o.size() - 1
		o.eachWithIndex { token, i ->
			target.append token
			if (i != last) {
				target.append tokenSep   //Add level token seperator
			}
		}
	}

	/**
	 * Populates this LevelPermission with data represented by passed in values. Removes any previously
	 * set level values in this instance. Each sector should be a individual string and NOT contain sector seperators
	 *
	 * @first First sector represented as string, may contain token seperators
	 * @second Second sector represented as string, may contain token seperators
	 * @third Third sector represented as string, may contain token seperators
	 * @fourth Fourth sector represented as string, may contain token seperators
	 * @fifth Fifth sector represented as string, may contain token seperators
	 * @sixth Sixth sector represented as string, may contain token seperators
	 *
	 * @return void - Will populate errors if problems found in any sector, does not persist object
	 */
	void populate(first, second, third, fourth, fifth, sixth) {

		if (containsLevelSep(first, 'first')) {
			return
		}

		this.first = splitByTokenSep(first)

		if (second) {
			if (containsLevelSep(second, 'second')) {
				return
			}

			this.second = splitByTokenSep(second)

			if (third) {
				if (containsLevelSep(third, 'third')) {
					return
				}

				this.third = splitByTokenSep(third)

				if (fourth) {
					if (containsLevelSep(fourth, 'fourth')) {
						return
					}

					this.fourth = splitByTokenSep(fourth)

					if (fifth) {
						if (containsLevelSep(fifth, 'fifth')) {
							return
						}

						this.fifth = splitByTokenSep(fifth)

						if (sixth) {
							if (containsLevelSep(sixth, 'sixth')) {
								return
							}

							this.sixth = splitByTokenSep(sixth)
						}
					}
				}
			}
		}

		buildTarget()
	}

	private boolean containsLevelSep(o, String name) {
		if (!o || o.contains(levelSep)) {
			errors.rejectValue('target', "nimble.levelpermission.invalid.${name}.sector")
			return true
		}
	}

	private List splitByTokenSep(o) {
		o.split tokenSep
	}
}
