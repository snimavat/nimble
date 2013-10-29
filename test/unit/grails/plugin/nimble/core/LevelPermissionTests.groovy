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
		def p = new LevelPermission(tokenFirst:first, tokenSecond:second, tokenThird:third, tokenFourth:fourth, tokenFifth:fifth, tokenSixth:sixth)
		p.owner = owner
		p.buildTarget()
		p
	}

	void testLevelPermissionCreation() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.tokenFirst.containsAll(first)
		assertTrue levelPermission.tokenSecond.containsAll(second)
		assertTrue levelPermission.tokenThird.containsAll(third)
		assertTrue levelPermission.tokenFourth.containsAll(fourth)
		assertTrue levelPermission.tokenFifth.containsAll(fifth)
		assertTrue levelPermission.tokenSixth.containsAll(sixth)

		// Do some random checks to ensure there isn't any contamination
		assertFalse levelPermission.tokenFirst.contains(sixth)
		assertFalse levelPermission.tokenSecond.contains(fifth)
		assertFalse levelPermission.tokenThird.contains(fourth)
		assertFalse levelPermission.tokenFourth.contains(third)
		assertFalse levelPermission.tokenFifth.contains(second)
		assertFalse levelPermission.tokenSixth.contains(first)

		assertEquals levelPermission.type, Permission.defaultPerm
	}

	void testFirstConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.tokenFirst = ['localtarget']
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()

		levelPermission.tokenFirst = []
		levelPermission.buildTarget()
		assertFalse levelPermission.validate()

		levelPermission.tokenFirst = null
		levelPermission.buildTarget()
		assertFalse levelPermission.validate()
	}

	void testSecondConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.tokenSecond = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testThirdConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.tokenThird = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testFourthConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.tokenFourth = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testFifthConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.tokenFifth = null
		levelPermission.buildTarget()
		assertTrue levelPermission.validate()
	}

	void testSixthConstraint() {
		def levelPermission = createValidLevelPermission()

		assertTrue levelPermission.validate()

		levelPermission.tokenSixth = null
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

		levelPermission.tokenSecond = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullThird() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04'

		levelPermission.tokenThird = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullFourth() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04:token05,token06'

		levelPermission.tokenFourth = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullFifth() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08'

		levelPermission.tokenFifth = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testValidTargetGenerationNullSixth() {
		def levelPermission = createValidLevelPermission()
		String expected = 'token01,token02:token03,token04:token05,token06:token07,token08:token09,token10'

		levelPermission.tokenSixth = null
		levelPermission.buildTarget()

		assertEquals levelPermission.target, expected
	}

	void testVariableTokenLengthTargetGeneration() {
		def levelPermission = new LevelPermission()
		String expected = 'token01:token02,token03,token04:token05,token06'

		levelPermission.tokenFirst = ['token01']
		levelPermission.tokenSecond = ['token02','token03','token04']
		levelPermission.tokenThird = ['token05','token06']
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
