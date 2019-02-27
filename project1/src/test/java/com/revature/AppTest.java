package com.revature;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest 
{
	Reimbursment r = new Reimbursment();
	/*@Test
	public void canCreateReimbursmentRequests() {
		assertEquals(r.create(17.34, 1), 0);
	}*/
	/*@Test
	public void cannotCreateReimbursmentRequestWithNegativeMoney() {
		assertEquals(r.create(-12.33, 1), -1);
	}
	@Test
	public void cannotCreateReimbursmentRequestWithZeroMoney() {
		assertEquals(r.create(0, 1), -1);
	}
	@Test
	public void canUpdateExistingReimbursmentRequest() {
		assertEquals(r.update(13.22, 1, 1), 0);
	}
	@Test
	public void cannotUpdateExistingReimbursmentRequestWithNegativeMoney() {
		assertEquals(r.update(-4.55, 1, 1), -1);
	}
	@Test
	public void cannotUpdateExistingReimbursmentRequestWithZeroMoney() {
		assertEquals(r.update(0, 1, 1), -1);
	}
	@Test
	public void canApproveAReimbursementRequest() {
		assertEquals(r.update(1, 1, "approve"), 0);
	}*/
	/*@Test
	public void canDenyAReimbursementRequest() {
		assertEquals(r.update(1, 1, "deny"), 0);
	}*/
	@Test
	public void canGetAllReimbursementRequests() {
		assertEquals(r.read(true, 1, "all pending requests"), 0);
	}
	/*@Test
	public void canDeleteAReimbursementRequests() {
		assertEquals(r.delete(13.22, 1, 2), 0);
	}*/
}
