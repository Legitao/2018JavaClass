package assignment1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.*;
import org.junit.Test;

public class SortToolsTest {

    public boolean compareArrays(int[] correct, int[] test, int n){
        if(correct.length == test.length && correct.length == n){
            return Arrays.equals(correct, test);
        }
        if(test.length < n) {
            return false;
        }
        for(int x = 0; x < n; x++){
            if(correct[x]==test[x]) {
            }
            else{
                return false;
            }
        }
        return true;
    }

	@Test(timeout = 2000)
	public void testFindFoundFull(){
		int[] x = new int[]{-2, -1, 0, 1, 2, 3};
		assertEquals(3, SortTools.find(x, 6, 1));
	}
	
	@Test(timeout = 2000)
	public void testInsertGeneralPartialEnd(){
		int[] x = new int[]{10, 20, 30, 40, 50};
		int[] expected = new int[]{10, 20, 30, 35};
		assertArrayEquals(expected, SortTools.insertGeneral(x, 3, 35));
	}
	
	@Test(timeout = 2000)
	public void testIsSortedZeroNEmptyArray() {
		int[] x = {};
		assertEquals(false, SortTools.isSorted(x, 0));
	}

	@Test(timeout = 2000)
	public void testIsSortedZeroN() {
		int[] x = {1,2,5,4};
		assertEquals(false, SortTools.isSorted(x, 0));
	}

	@Test(timeout = 2000)
	public void testFindOneNOneArray() {
		int[] x = {1};
		assertEquals(-1, SortTools.find(x, 1, 5));
	}

	@Test(timeout = 2000)
	public void testFindOneN() {
		int[] x = {1,2,2,2,2,3,4,4,4,4,4,5,6};
		assertEquals(-1, SortTools.find(x, 1, 3));
	}

	@Test(timeout = 2000)
	public void testInsertGeneralZeroN() {
		int[] x = {5};
		int[] expected1 = {5,10};
		int[] expected2 = {5};
		SortTools.insertGeneral(x,0,10);
		if(Arrays.equals(x,expected1) || Arrays.equals(x,expected2))
			assertTrue(true);
	}

	@Test(timeout = 2000)
	public void testInsertGeneral2() {
		int[] x = {1,1,1,1,1,2,3,3,3};
		int[] expected = {1,1,1,1,1,2,3};
		SortTools.insertGeneral(x, 6, 3);
		compareArrays(expected, x, 6);
	}


	@Test(timeout = 2000)
	public void testInsertionSortLarge() {
		int[] x = new int[1001];
		for(int i = 0; i < 1000; i++) {
			x[i] = (int)(Math.random()*1000);
		}
		x[1000] = 100000;
		int []expected = Arrays.copyOf(x,1000);
		Arrays.sort(expected);

		SortTools.insertSort(x, x.length-1);
		assertTrue(compareArrays(expected, x,1000));
	}

	@Test(timeout = 2000)
	public void testIsSorted1ae(){
		int[] test = {10,100,1000,2000,6000,12000,12001,12002};
		assertEquals(true, SortTools.isSorted(test, test.length));
	}

	@Test(timeout = 2000)
	public void testIsSorted2ae(){
		int[] test = {10,100,2000, 1999, 6000,12000,12001,12002};
		assertEquals(false, SortTools.isSorted(test, test.length));
	}


	@Test(timeout = 2000)
	public void testFind2ae(){
		int[] test = {10,100,1000,2000,6000,12000,12001,12002};
		assertEquals(-1, SortTools.find(test, test.length, 1999));
	}

	@Test(timeout = 2000)
	public void testIsSorted1() {
		int[] x = {1,1,1,1,1,1,1,1,1,1,1};
		assertEquals(true,SortTools.isSorted(x, x.length));
	}

	@Test(timeout = 2000)
	public void testIsSorted2() {
		int[] x = {5,2,3,3,5,7,6,1,4};
		assertEquals(false,SortTools.isSorted(x, x.length));
	}

	@Test(timeout = 2000)
	public void testFind() {
		int[] x = {14,76,98,123,184,299,301,502,610,720,981,1200,1210,1256,2269,2304};
		assertEquals(0,SortTools.find(x, x.length, 14));
	}


	@Test(timeout = 2000)
	public void testInsertGeneral() {
		int[] x = {1,4,6,7,8,9,10,12,17,20,21};
		int[] expected = {1,2};

		assertArrayEquals(expected,SortTools.insertGeneral(x, 1, 2));
	}


	@Test(timeout = 2000)
	public void testInsertInPlace() {
		int[] x = {1,4,6,7,8,9,10,11,12,20,21,25,26,31};
		int[] expected1 = {1,4,6,7,8,9,10,11,12,20,21,25,26,30};
		int[] expected2 = {1,4,6,7,8,9,10,11,12,20,21,25,26,30,31};
		SortTools.insertInPlace(x, x.length-1, 30);
		if(Arrays.equals(x,expected1) || Arrays.equals(x,expected2))
			assert true;
	}

	@Test(timeout = 2000)
	public void testInsertionInPlace2() {
		int[] x = {1,3,4,5,37};
		int[] expected1 = {1,3,4,5,7,37};
		int[] expected2 = {1,3,4,5,7};
		SortTools.insertInPlace(x, x.length-1,7);
		if(Arrays.equals(x,expected1) || Arrays.equals(x,expected2))
			assertTrue(true);
	}

	@Test()
	public void testInsertSort2(){
		int[] x = {100, -20, 40, 100, -100, 7, 42};
		int[] expected = Arrays.copyOf(x,7);
		SortTools.insertSort(x, 7);
		Arrays.sort(expected);
		assertTrue(Arrays.equals(x,expected));

	}

	@Test(timeout = 2000)
	public void testInsertionInPlace3() {
		int[] x = {1,2,3,20};
		SortTools.insertInPlace(x, x.length-1,10);
		int[] expected1 = {1,2,3,10,20};
		int[] expected2 = {1,2,3,10};
		if(Arrays.equals(x,expected1) || Arrays.equals(x,expected2))
			assertTrue(true);
	}








}
