package com.itacademy.automation.unit_tests.girl_tests;

import com.itacademy.automation.unit_task.entities.Boy;
import com.itacademy.automation.unit_task.entities.Girl;
import com.itacademy.automation.unit_task.entities.Mood;
import com.itacademy.automation.unit_task.helpers.exceptions.BoyfriendIsNullException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.itacademy.automation.unit_task.helpers.random.RandomGenerator.*;
import static org.testng.Assert.assertEquals;

public class GirlTest {

    private Girl girl;
    private Boy boy;

    @DataProvider(name = "getMoodTest")
    public Object[][] getMoodTestData() {
        return new Object[][]{
                {new Girl(true, generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthMoreThanMillion())), Mood.EXCELLENT},
                {new Girl(false, generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthMoreThanMillion())), Mood.GOOD},
                {new Girl(true, generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthBetweenZeroAndMillion())), Mood.GOOD},
                {new Girl(false, true), Mood.NEUTRAL},
                {new Girl(false, false), Mood.I_HATE_THEM_ALL},
                {new Girl(false, false, new Boy(generateRandomMonth(), generateRandomWealthBetweenZeroAndMillion())), Mood.I_HATE_THEM_ALL},
        };
    }

    @Test(dataProvider = "getMoodTest")
    public void getMoodTest(Girl girl, Mood expectedMood) {
        assertEquals(girl.getMood(), expectedMood, "Unexpected girlfriend's mood");
    }

    @DataProvider(name = "isSlimFriendBecameFatTest")
    public Object[][] isSlimFriendBecameFatTestData() {
        return new Object[][]{
                {new Girl(false, true), true},
                {new Girl(false, false), false},
                {new Girl(true, false), false},
                {new Girl(true, true), false},
        };
    }

    @Test(dataProvider = "isSlimFriendBecameFatTest")
    public void isSlimFriendBecameFatTest(Girl girl, boolean expectedResult) {
        assertEquals(girl.isSlimFriendBecameFat(), expectedResult, "Unexpected girlfriend's slimness");
    }

    @DataProvider(name = "isBoyFriendWillBuyNewShoesTest")
    public Object[][] isBoyFriendWillBuyNewShoesTestData() {
        return new Object[][]{
                {new Girl(true, generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthMoreThanMillion())), true},
                {new Girl(true, generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthBetweenZeroAndMillion())), false},
                {new Girl(false, generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthBetweenZeroAndMillion())), false},
                {new Girl(false, generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthMoreThanMillion())), false},
                {new Girl(true), false},
        };
    }

    @Test(dataProvider = "isBoyFriendWillBuyNewShoesTest")
    public void isBoyFriendWillBuyNewShoesTest(Girl girl, boolean expectedResult) {
        assertEquals(girl.isBoyFriendWillBuyNewShoes(), expectedResult, "Unexpected decision to buy new shoes");
    }

    @DataProvider(name = "isBoyfriendRichTest")
    public Object[][] isBoyfriendRichTestData() {
        return new Object[][]{
                {new Girl(), false},
                {new Girl(generateRandomBooleanValue(), generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthMoreThanMillion())), true},
                {new Girl(generateRandomBooleanValue(), generateRandomBooleanValue(), new Boy(generateRandomMonth(), generateRandomWealthBetweenZeroAndMillion())), false},
        };
    }

    @Test(dataProvider = "isBoyfriendRichTest")
    public void isBoyfriendRichTest(Girl girl, boolean expectedResult) {
        assertEquals(girl.isBoyfriendRich(), expectedResult, "Unexpected boyfriend's richness");
    }

    @Test(description = "boyfriend = null", expectedExceptions = BoyfriendIsNullException.class, groups = "spendBoyFriendMoneyTestGroup")
    public void spendBoyFriendMoneyIfBoyfriendIsNullTest() {
        girl = new Girl(generateRandomBooleanValue(), generateRandomBooleanValue());
        double amountForSpending = generateRandomWealthBetweenZeroAndMillion();
        girl.spendBoyFriendMoney(amountForSpending);
    }

    @Test(description = "amount for spending > wealth", expectedExceptions = RuntimeException.class, groups = "spendBoyFriendMoneyTestGroup")
    public void spendBoyFriendMoneyIfAmountForSpendingMoreThanWealthTest() {
        double boyWealth = generateRandomWealthMoreThanMillion();
        boy = new Boy(generateRandomMonth(), boyWealth);
        girl = new Girl(generateRandomBooleanValue(), generateRandomBooleanValue(), boy);
        double amountForSpending = boyWealth + generateRandomPositiveWealth();
        girl.spendBoyFriendMoney(amountForSpending);
    }

    @Test(description = "amount for spending < wealth", groups = "spendBoyFriendMoneyTestGroup")
    public void spendBoyFriendMoneyIfAmountForSpendingLessThanWealthTest() {
        double boyWealth = generateRandomWealthMoreThanMillion();
        boy = new Boy(generateRandomMonth(), boyWealth);
        girl = new Girl(generateRandomBooleanValue(), generateRandomBooleanValue(), boy);
        double amountForSpending = generateRandomWealthBetweenZeroAndMillion();
        girl.spendBoyFriendMoney(amountForSpending);
        assertEquals(boy.getWealth(), boyWealth - amountForSpending, "Unexpected wealth after spending boyfriend's money");
    }

    @Test(description = "isBoyFriendRich() = false", groups = "spendBoyFriendMoneyTestGroup")
    public void spendBoyFriendMoneyIfBoyfriendIsNotReachTest() {
        double boyWealth = generateRandomWealthBetweenZeroAndMillion();
        boy = new Boy(generateRandomMonth(), boyWealth);
        girl = new Girl(generateRandomBooleanValue(), generateRandomBooleanValue(), boy);
        girl.spendBoyFriendMoney(generateRandomWealthBetweenZeroAndMillion());
        assertEquals(boy.getWealth(), boyWealth, "Unexpected wealth after spending boyfriend's money");
    }
}
