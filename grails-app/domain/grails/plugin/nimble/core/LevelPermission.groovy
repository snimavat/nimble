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

import grails.util.Holders as ConfigurationHolder // Since Grails 2.x ConfigurationHolder is deprecated 

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
		tokenFirst: String,
		tokenSecond: String,
		tokenThird: String,
		tokenFourth: String,
		tokenFifth: String,
		tokenSixth: String
	]

	static mapping = {
		cache usage: 'read-write', include: 'all'
		table ConfigurationHolder.config.nimble.tablenames.levelpermission
	}

	static constraints = {
		tokenFirst(minSize: 1)
		tokenSecond(nullable: true)
		tokenThird(nullable: true)
		tokenFourth(nullable: true)
		tokenFifth(nullable: true)
		tokenSixth(nullable: true)
	}

	LevelPermission() {
		type = Permission.defaultPerm
	}

	void buildTarget() {
		StringBuilder target = new StringBuilder()

		if (tokenFirst) {
			addTokens tokenFirst, target, false
			if (tokenSecond) {
				addTokens tokenSecond, target
				if (tokenThird) {
					addTokens tokenThird, target
					if (tokenFourth) {
						addTokens tokenFourth, target
						if (tokenFifth) {
							addTokens tokenFifth, target
							if (tokenSixth) {
								addTokens tokenSixth, target
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

		this.tokenFirst = splitByTokenSep(first)

		if (second) {
			if (containsLevelSep(second, 'second')) {
				return
			}

			this.tokenSecond = splitByTokenSep(second)

			if (third) {
				if (containsLevelSep(third, 'third')) {
					return
				}

				this.tokenThird = splitByTokenSep(third)

				if (fourth) {
					if (containsLevelSep(fourth, 'fourth')) {
						return
					}

					this.tokenFourth = splitByTokenSep(fourth)

					if (fifth) {
						if (containsLevelSep(fifth, 'fifth')) {
							return
						}

						this.tokenFifth = splitByTokenSep(fifth)

						if (sixth) {
							if (containsLevelSep(sixth, 'sixth')) {
								return
							}

							this.tokenSixth = splitByTokenSep(sixth)
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
