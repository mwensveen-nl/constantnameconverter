package nl.mwensveen.constantnameconverter;

import org.junit.Assert;
import org.junit.Test;

public class ConverterTest {

	@Test
	public void testNull() {
		Converter fixture = new Converter(null);
		String variable = fixture.convert();
		Assert.assertNull(variable);
	}

	@Test
	public void testBlank() {
		Converter fixture = new Converter(" ");
		String variable = fixture.convert();
		Assert.assertNull(variable);
	}

	@Test
	public void testNotAVariable() {
		Converter fixture = new Converter("01_AB");
		String variable = fixture.convert();
		Assert.assertNull(variable);
	}

	@Test
	public void testFromConstant() {
		Converter fixture = new Converter("AB_CD");
		String variable = fixture.convert();
		Assert.assertEquals("abCd", variable);
	}

	@Test
	public void testFromConstant2() {
		Converter fixture = new Converter("AB_CD_01_DEFG");
		String variable = fixture.convert();
		Assert.assertEquals("abCd01Defg", variable);
	}

	@Test
	public void testFromConstant3() {
		Converter fixture = new Converter("_AB_CD_01_DEFG");
		String variable = fixture.convert();
		Assert.assertEquals("abCd01Defg", variable);
	}

	@Test
	public void testFromConstant4() {
		Converter fixture = new Converter("DEFG");
		String variable = fixture.convert();
		Assert.assertEquals("defg", variable);
	}

	@Test
	public void testFromVariable() {
		Converter fixture = new Converter("abCdeFgHij02Kl");
		String variable = fixture.convert();
		Assert.assertEquals("AB_CDE_FG_HIJ_0_2_KL", variable);
	}

	@Test
	public void testFromVariable2() {
		Converter fixture = new Converter("AbCdeFgHij02Kl");
		String variable = fixture.convert();
		Assert.assertEquals("AB_CDE_FG_HIJ_0_2_KL", variable);
	}

	@Test
	public void testFromVariable3() {
		Converter fixture = new Converter("abCdeFgHij02KL");
		String variable = fixture.convert();
		Assert.assertEquals("AB_CDE_FG_HIJ_0_2_K_L", variable);
	}

	@Test
	public void testFromVariable4() {
		Converter fixture = new Converter("abCdeFgHij02K_L");
		String variable = fixture.convert();
		Assert.assertEquals("AB_CDE_FG_HIJ_0_2_K__L", variable);
	}

	@Test
	public void testFromVariable5() {
		Converter fixture = new Converter("abCdeFgHij02K_l");
		String variable = fixture.convert();
		Assert.assertEquals("AB_CDE_FG_HIJ_0_2_K_L", variable);
	}

	@Test
	public void testWithSpace1() {
		Converter fixture = new Converter("AB_CD  ");
		String variable = fixture.convert();
		Assert.assertEquals("abCd  ", variable);
	}

	@Test
	public void testWithSpace2() {
		Converter fixture = new Converter("   AB_CD");
		String variable = fixture.convert();
		Assert.assertEquals("   abCd", variable);
	}

	@Test
	public void testWithSpace3() {
		Converter fixture = new Converter("  AB_CD   ");
		String variable = fixture.convert();
		Assert.assertEquals("  abCd   ", variable);
	}

	@Test
	public void testLowerCaseWithUnderscore() throws Exception {
		Converter fixture = new Converter("ab_cd_ef");
		String variable = fixture.convert();
		Assert.assertEquals("AB_CD_EF", variable);
	}

	@Test
	public void testStartWithUpderWithUnderscore() throws Exception {
		Converter fixture = new Converter("Ab_cd_ef");
		String variable = fixture.convert();
		Assert.assertEquals("AB_CD_EF", variable);
	}
}
