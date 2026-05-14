import { test, expect } from '@playwright/test';

test.describe('Authentication', () => {
  test.beforeEach(async ({ page }) => {
    await page.addInitScript(() => localStorage.clear());
  });

    test('successful login → redirects to dashboard', async ({ page }) => {
    await page.goto('/sign-in');
    await page.locator('input[type="email"]').fill('test@example.com');
    await page.locator('input[type="password"]').fill('password123');
    await page.getByRole('button', { name: 'Sign In' }).click();
    
    await expect(page).toHaveURL(/dashboard/);
    const jwtToken = await page.evaluate(() => localStorage.getItem('jwt'));
    expect(jwtToken).toBeTruthy();
    });


  test('login failure → shows error', async ({ page }) => {
    await page.goto('/sign-in');
    
    await page.locator('input[type="email"]').fill('wrong@example.com');
    await page.locator('input[type="password"]').fill('wrongpass');
    await page.getByRole('button', { name: 'Sign In' }).click();
    
    await expect(page.locator('.bg-red-100')).toBeVisible({ timeout: 5000 });
  });
});