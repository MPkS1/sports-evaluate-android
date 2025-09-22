# 🧪 Sports Evaluate - Testing Guide

## 🚀 Pre-Testing Setup

### 1. **Verify Build Success**
```bash
1. Open Android Studio
2. Click "Build" → "Make Project" (Ctrl+F9)
3. Check "Build" tab at bottom - should show "BUILD SUCCESSFUL"
4. If errors appear, they'll be listed here
```

### 2. **Check Firebase Connection**
```bash
1. In Android Studio, go to Tools → Firebase
2. Click "Authentication" → "Connect to Firebase"
3. Should show "Connected" if properly configured
```

## 📱 Step-by-Step Testing

### **Test 1: App Launch & Navigation**

#### ✅ **Expected Result**: App launches to Login Screen
```bash
1. Run the app (green play button)
2. App should open to Login Screen with:
   - "Sports Evaluate" title
   - Email field
   - Password field
   - "Sign In" button
   - "Don't have an account? Register" link
```

#### ❌ **If Failed**:
- Check Logcat for errors
- Verify Firebase configuration
- Ensure internet connection

---

### **Test 2: Registration Flow**

#### **Step 2.1: Navigate to Register**
```bash
1. Click "Don't have an account? Register"
2. Should navigate to Register Screen with:
   - Email field
   - Password field
   - Confirm Password field
   - "Register" button
```

#### **Step 2.2: Test Form Validation**
```bash
1. Try registering without filling fields → Button should be disabled
2. Enter mismatched passwords → Error message should appear
3. Enter valid data:
   - Email: test@example.com
   - Password: password123
   - Confirm: password123
4. Click "Register"
```

#### ✅ **Expected Result**: Navigation to Code Verification Screen

---

### **Test 3: Email Verification**

#### **Step 3.1: Check Verification Code Generation**
```bash
1. After registration, check Android Studio Logcat:
   - Go to Logcat tab (bottom of Android Studio)
   - Filter by "EmailService" or search "Verification code"
   - You should see: "Verification code for test@example.com: 123456"
```

#### **Step 3.2: Test Code Verification**
```bash
1. Code Verification Screen should show:
   - Email address
   - 6-digit input field
   - 60-second countdown timer
   - "Verify Code" button
   - "Resend Code" button

2. Test invalid code:
   - Enter wrong code (e.g., 111111)
   - Should show error message

3. Test valid code:
   - Enter the code from Logcat
   - Should navigate to Profile Setup
```

#### **Step 3.3: Test Code Expiry**
```bash
1. Wait for 60-second timer to reach 0
2. Try entering code → Should show "expired" error
3. Click "Resend Code" → New code should appear in Logcat
```

---

### **Test 4: Profile Setup Flow**

#### **Step 4.1: Aadhaar Details Screen**
```bash
1. Should show form with:
   - Full Name field
   - Age field (numbers only)
   - Gender dropdown (Male/Female/Other)
   - Address field (multiline)
   - Image upload area

2. Test validation:
   - Try clicking "Next" without filling → Button disabled
   - Fill all fields except image → Button still disabled
   - Upload any image from gallery → Button should enable

3. Complete form with test data:
   - Name: "John Doe"
   - Age: "25"
   - Gender: "Male"
   - Address: "123 Test Street, City"
   - Upload any image
```

#### **Step 4.2: Height Screen**
```bash
1. Should show:
   - Height icon
   - "Height Measurement" title
   - Input field with "cm" suffix
   - "Back" and "Next" buttons

2. Test:
   - Enter height: "175"
   - Click "Next"
```

#### **Step 4.3: Weight Screen**
```bash
1. Should show:
   - Weight icon
   - "Weight Measurement" title
   - Input field with "kg" suffix
   - "Back" and "Next" buttons

2. Test:
   - Enter weight: "70"
   - Click "Next"
```

#### **Step 4.4: Fitness Test Selection**
```bash
1. Should show:
   - List of fitness tests with checkboxes:
     • Push-ups
     • Sit-ups
     • Long Jump
   - "Back" and "Proceed" buttons

2. Test:
   - Select at least one test
   - Click "Proceed"
```

#### **Step 4.5: Summary Screen**
```bash
1. Should display all entered information:
   - Personal Information card
   - Uploaded image
   - Physical measurements
   - Selected fitness tests
   - "Back" and "Submit" buttons

2. Test:
   - Review all data is correct
   - Click "Submit"
```

#### ✅ **Expected Result**: Navigation to Dashboard

---

### **Test 5: Dashboard Functionality**

#### **Step 5.1: Profile Display**
```bash
1. Dashboard should show:
   - "Sports Evaluate" title with logout button
   - Profile Information card with all details
   - Uploaded image displayed
   - Fitness Tests section
   - AI Analysis placeholder
```

#### **Step 5.2: Test Logout**
```bash
1. Click logout button (top-right)
2. Should navigate back to Login Screen
3. Try navigating back → Should stay on Login
```

---

### **Test 6: Re-login Flow**

#### **Test Existing User Login**
```bash
1. Use same credentials from registration:
   - Email: test@example.com
   - Password: password123
2. Click "Sign In"
3. Should go directly to Dashboard (skip profile setup)
```

---

## 🔍 Debugging Common Issues

### **Issue 1: App Crashes on Launch**
```bash
**Check Logcat for:**
- Firebase initialization errors
- Missing dependencies
- Package name mismatches

**Solutions:**
- Verify google-services.json is correct
- Check internet connection
- Clean and rebuild project
```

### **Issue 2: Registration Fails**
```bash
**Check Logcat for:**
- Firebase Auth errors
- Network connectivity issues

**Solutions:**
- Verify Firebase Auth is enabled in console
- Check email format validity
- Ensure password meets Firebase requirements (6+ chars)
```

### **Issue 3: Verification Code Not Found**
```bash
**Check Logcat:**
- Search for "EmailService"
- Look for "Verification code for" messages

**If not found:**
- Email service might not be running
- Check EmailService.kt implementation
```

### **Issue 4: Navigation Issues**
```bash
**Symptoms:**
- Stuck on one screen
- Back button not working

**Solutions:**
- Check ViewModels are updating state correctly
- Verify navigation logic in SportsEvaluateNavigation.kt
```

### **Issue 5: Image Upload Fails**
```bash
**Check:**
- Gallery permissions
- Image picker implementation
- File URI handling

**Test with different image types:**
- JPG, PNG files
- Different sizes
```

## 📊 Firebase Console Verification

### **Check User Registration**
```bash
1. Go to Firebase Console → Authentication → Users
2. Should see registered user with email
3. User status should show as verified
```

### **Monitor Real-time Usage**
```bash
1. Firebase Console → Analytics (if enabled)
2. See active users
3. Monitor authentication events
```

## ✅ Success Criteria Checklist

Your app is working correctly if:

- [ ] **App launches without crashes**
- [ ] **Registration creates new user**
- [ ] **Verification code appears in Logcat**
- [ ] **Code verification works within 60 seconds**
- [ ] **All profile setup screens navigate properly**
- [ ] **Form validation prevents invalid submissions**
- [ ] **Image upload functionality works**
- [ ] **Dashboard displays all profile information**
- [ ] **Logout returns to login screen**
- [ ] **Re-login works with existing credentials**
- [ ] **No compilation errors in Android Studio**
- [ ] **Firebase Console shows registered users**

## 🎯 Performance Testing

### **Test on Different Devices**
```bash
- Android 8.0+ devices
- Different screen sizes
- Various Android versions
- Emulator vs physical device
```

### **Test Edge Cases**
```bash
- Poor internet connection
- App backgrounding during registration
- Rotating device during form filling
- Invalid image formats
- Very long text inputs
```

## 📝 Test Data Suggestions

### **Use These Test Accounts:**
```bash
Email: test1@example.com, Password: password123
Email: user2@test.com, Password: mypassword
Email: demo@sports.app, Password: demo123456
```

### **Profile Test Data:**
```bash
Name: "Alice Johnson", Age: 28, Gender: Female
Name: "Bob Smith", Age: 35, Gender: Male  
Name: "Chris Lee", Age: 22, Gender: Other
```

## 🚨 Red Flags (Issues to Report)

- App crashes during any step
- Verification codes not generating
- Firebase authentication errors
- Navigation getting stuck
- Data not persisting between screens
- Images not uploading
- UI elements not responding

Your Sports Evaluate app should pass all these tests if properly configured! 🎉