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

import grails.test.mixin.TestFor

/**
 * @author Bradley Beddoes
 */
@TestFor(LevelPermission)
class LevelPermissionTests extends GroovyTestCase {
	private first =  ['token01', 'token02']
	private second = ['token03', 'token04']
	private third =  ['token05', 'token06']
	private fourth = ['token07', 'token08']
	private fifth =  ['token09', 'token10']
	private sixth =  ['token11', 'token12']

	private owner = new Expando(name: "Test owner")

	private LevelPermission createValidLevelPermission() {
		def p = new LevelPermission(first:first, second:second, third:third, fourth:fourth, fifth:fifth, sixth:sixth)
		p.owner = owner
		p.buildTarget()
		p
	}

	void testLevelPermissionCreation() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.first.containsAll(first)
		assertTrue levelPermission.second.containsAll(second)
		assertTrue levelPermission.third.containsAll(third)
		assertTrue levelPermission.fourth.containsAll(fourth)
		assertTrue levelPermission.fifth.containsAll(fifth)
		assertTrue levelPermission.sixth.containsAll(sixth)

		// Do some random checks to ensure there isn't any contamination
		assertFalse levelPermission.first.contains(sixth)
		assertFalse levelPermission.second.contains(fifth)
		assertFalse levelPermission.third.contains(fourth)
		assertFalse levelPermission.fourth.contains(third)
		assertFalse levelPermission.fifth.contains(second)
		assertFalse levelPermission.sixth.contains(first)

		assertEquals levelPermission.type, Permission.defaultPerm
	}

	void testFirstConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.first = ['localtarget']
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()

		levelPermission.first = []
		levelPermission.buildTarget()
		assertFalse levelPermission.validate()

		levelPermission.first = null
		levelPermission.buildTarget()
		assertFalse levelPermission.validate()
	}

	void testSecondConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.second = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testThirdConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.third = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testFourthConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.fourth = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testFifthConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.fifth = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testSixthConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.sixth = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testValidTargetGeneration() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08:token09,token10:token11,token12'

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullSecond() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02'

		levelPermission.second = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullThird() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04'

		levelPermission.third = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullFourth() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04:token05,token06'

		levelPermission.fourth = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullFifth() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08'

		levelPermission.fifth = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullSixth() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08:token09,token10'

		levelPermission.sixth = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testVariableTokenLengthTargetGeneration() {
		def levelPermission = new LevelPermission()
		String expected = 'token01:token02,token03,token04:token05,token06'

		levelPermission.first = ['token01']
		levelPermission.second = ['token02','token03','token04']
		levelPermission.third = ['token05','token06']
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidPopulation() {
		def levelPermission = new LevelPermission()
		levelPermission.populate('token01,token02', 'token03,token04','token05,token06','token07,token08','token09,token10','token11,token12')

		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08:token09,token10:token11,token12'
		assertEquals levelPermission.target, expected
	}

	void testValidPopulationFirstSector() {
		def levelPermission = new LevelPermission()
		levelPermission.populate('token01,token02', null, null, null, null, null)

		String expected = 'token01,token02'
		assertEquals levelPermission.target, expected
	}

	void testValidPopulationTwoSectors() {
		def levelPermission = new LevelPermission()
		levelPermission.populate('token01,token02', 'token03,token04', null, null, null, null)

		String expected = 'token01,token02:token03,token04'
		assertEquals levelPermission.target, expected
	}

	void testValidPopulationThreeSectors() {
		def levelPermission = new LevelPermission()
		levelPermission.populate('token01,token02', 'token03,token04', 'token05,token06', null, null, null)

		String expected = 'token01,token02:token03,token04:token05,token06'
		assertEquals levelPermission.target, expected
	}

	void testValidPopulationFourSectors() {
		def levelPermission = new LevelPermission()
		levelPermission.populate('token01,token02', 'token03,token04', 'token05,token06', 'token07,token08', null, null)

		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08'
		assertEquals levelPermission.target, expected
	}

	void testValidPopulationFiveSectors() {
		def levelPermission = new LevelPermission()
		levelPermission.populate('token01,token02', 'token03,token04', 'token05,token06', 'token07,token08', 'token09,token10', null)

		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08:token09,token10'
		assertEquals levelPermission.target, expected
	}

	void testInvalidPopulationFirstSector() {
		def levelPermission = new LevelPermission()
		mockDomain LevelPermission, [levelPermission]
		levelPermission.populate('token01,token02, token:xyz', null, null, null, null, null)

		assertNull levelPermission.target
		assertTrue levelPermission.hasErrors()
		assertTrue levelPermission.errors.allErrors.collect { it.code }.contains('nimble.levelpermission.invalid.first.sector')
	}

	void testInvalidPopulationSecondSector() {
		def levelPermission = new LevelPermission()
		mockDomain LevelPermission, [levelPermission]
		levelPermission.populate('token01,token02', 'token:02', null, null, null, null)

		assertNull levelPermission.target
		assertTrue levelPermission.hasErrors()
		assertTrue levelPermission.errors.allErrors.collect { it.code }.contains('nimble.levelpermission.invalid.second.sector')
	}

	void testInvalidPopulationThirdSector() {
		def levelPermission = new LevelPermission()
		mockDomain LevelPermission, [levelPermission]
		levelPermission.populate('token01', 'token02', 'token:03', null, null, null)

		assertNull levelPermission.target
		assertTrue levelPermission.hasErrors()
		assertTrue levelPermission.errors.allErrors.collect { it.code }.contains('nimble.levelpermission.invalid.third.sector')
	}

	void testInvalidPopulationFourthSector() {
		def levelPermission = new LevelPermission()
		mockDomain LevelPermission, [levelPermission]
		levelPermission.populate('token01', 'token02', 'token03', 'token:04', null, null)

		assertNull levelPermission.target
		assertTrue levelPermission.hasErrors()
		assertTrue levelPermission.errors.allErrors.collect { it.code }.contains('nimble.levelpermission.invalid.fourth.sector')
	}

	void testInvalidPopulationFifthSector() {
		def levelPermission = new LevelPermission()
		mockDomain LevelPermission, [levelPermission]
		levelPermission.populate('token01', 'token02', 'token03', 'token04', 'token:05', null)

		assertNull levelPermission.target
		assertTrue levelPermission.hasErrors()
		assertTrue levelPermission.errors.allErrors.collect { it.code }.contains('nimble.levelpermission.invalid.fifth.sector')
	}

	void testInvalidPopulationSixthSector() {
		def levelPermission = new LevelPermission()
		mockDomain LevelPermission, [levelPermission]
		levelPermission.populate('token01', 'token02', 'token03', 'token04', 'token05', 'token:06')

		assertNull levelPermission.target
		assertTrue levelPermission.hasErrors()
		assertTrue levelPermission.errors.allErrors.collect { it.code }.contains('nimble.levelpermission.invalid.sixth.sector')
	}
}
