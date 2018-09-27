package com.sirding;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

public class Demo {

	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtil.convertDateToString(new Date()));
		System.out.println(DateUtil.getDateDownOnMonth("2016-06-15"));
		Integer a = new Integer(1);
		Integer b = new Integer(1);
		System.out.println((a == b));
		String s = new String("11");
		System.out.println((s == "11"));
		getStr();
		String regex = "(^\\d{8}|(\\s){0}$)";
		System.out.println(" ".matches(regex));
		System.out.println("123".matches(regex));
		System.out.println("12345678".matches(regex));

        System.out.println(new BigDecimal("100.010").stripTrailingZeros().toPlainString());
        
        System.out.println(Integer.parseInt("05"));
        
        
        String m = "尊敬的用户您好！ 您的验证码是： %d,请您及时使用，请勿向任何人泄露。";
        System.out.println(String.format(m, 123));

        System.out.println(System.currentTimeMillis());
    }
	
	public static void getStr() {
		String tmp = "cf88f838-cbdb-11e6-b969-2c44fd7f4dcc, 14940cdf-aa69-11e6-b969-2c44fd7f4dcc, 556fe47a-98cc-11e5-8645-008cfae40e8c, a85f6092-0d68-11e7-b969-2c44fd7f4dcc, 40041ac2-9586-11e5-8c86-1051721c3a3e, 5579c490-52f8-11e5-9ecc-1051721c3a3e, 4037c794-5e33-11e7-b969-2c44fd7f4dcc, c868f3eb-0704-11e6-b969-2c44fd7f4dcc, 8be6290f-106a-4f30-b460-2872073ca40f, bbcc7076-6f07-11e5-9ecc-1051721c3a3e, f260557f-0be5-4807-b9b1-a534b45a8fd0, 9657c46b-b44f-11e6-b969-2c44fd7f4dcc, 32ce7f4c-f912-474f-a9c6-dd907b8b16bb, 2a246aa3-d824-438a-a0fe-1d326a3a59a5, cf69cfcf-5090-11e6-b969-2c44fd7f4dcc, a361ba30-6d05-11e7-b969-2c44fd7f4dcc, 5f1b651a-9598-48ec-b4b1-a24aa93b23f6, 28880d5d-ac30-4b70-b7aa-6c68434e3e32, 87f8443b-fe04-4c90-b6ea-c2858b2bbec0, aeefccd5-049b-11e7-b969-2c44fd7f4dcc, 60809fdf-9c19-11e7-b969-2c44fd7f4dcc, 258f3694-8571-4a19-9ced-053303b29e0e, 8f22302f-453f-11e7-b969-2c44fd7f4dcc, 1b9fd6f0-4b68-4b1c-aebd-28b7e9d4403d, e06be10e-ea88-4316-af1d-12c88edb76c6, 55e57e77-506c-49cb-9fd7-8b86c5c2c5ed, 8daea559-7797-11e5-9ecc-1051721c3a3e, 393d44c0-299a-11e7-b969-2c44fd7f4dcc, fdf28693-1f3d-11e7-b969-2c44fd7f4dcc, ae744037-bdcd-11e5-b969-2c44fd7f4dcc, ae4407a9-037d-462c-b33b-e0476d66da62, a9ace9cc-60cd-11e5-9ecc-1051721c3a3e, bcfc64ea-2b3d-11e7-b969-2c44fd7f4dcc, b0942e9e-6a2c-11e7-b969-2c44fd7f4dcc, 072b5b33-19e2-11e7-b969-2c44fd7f4dcc, a1059957-3dc3-11e7-b969-2c44fd7f4dcc, e2f9ff28-7f7b-11e5-9ecc-1051721c3a3e, a041dac6-4971-11e6-b969-2c44fd7f4dcc, 1249de17-3c36-11e7-b969-2c44fd7f4dcc, f1eb5826-4fbc-11e5-9ecc-1051721c3a3e, 34110c00-5d37-11e5-9ecc-1051721c3a3e, 0d9c6c48-e21b-418a-98ba-4770ad156c30, b92fb714-a489-468f-a38d-7511f3ae3134, 34faedf8-b78f-11e7-acae-70106fb15706, e1701c1c-7eea-4c39-80be-fead72cae03d, 8bca1b7f-80dd-4052-96b1-027070318be0, daa5c651-dac3-11e5-b969-2c44fd7f4dcc, f3d03b65-9dc6-11e6-b969-2c44fd7f4dcc, 85648def-7a59-11e6-b969-2c44fd7f4dcc, 6305b9c2-2972-11e7-b969-2c44fd7f4dcc, 0db4ba11-ee14-11e6-b969-2c44fd7f4dcc, f570f857-f0f0-46e5-b773-bd291b640f62, ef32a016-c594-11e5-b969-2c44fd7f4dcc, 0147d266-3b68-11e5-9ecc-1051721c3a3e, b3849143-a9bf-4809-85e1-adc602249296, cc374690-e8bc-11e5-b969-2c44fd7f4dcc, a4d21563-32c0-11e6-b969-2c44fd7f4dcc, d6473739-431e-11e5-9ecc-1051721c3a3e, 5a4f7396-76ed-4641-88b1-76c3f3765fca, 46df7845-2156-11e6-b969-2c44fd7f4dcc, 1538da0c-6a50-462f-a564-49c3b74f9d1c, f6213381-d53b-11e5-b969-2c44fd7f4dcc, bd15f90b-5f7a-4bdf-a53f-44fd1a50c9d5, f0f26765-981d-11e5-8645-008cfae40e8c, 88bcbfba-53e2-11e6-b969-2c44fd7f4dcc, c6e1c8d0-08fc-11e5-b70f-1051721c3a3e, 2ded23f5-18c6-477d-bafa-5c963ca81783, d96cd6a6-9a2a-11e5-8645-008cfae40e8c, d6b5a507-d7f9-11e4-90b2-d89d67270c78, 68862f22-771d-11e6-b969-2c44fd7f4dcc, 7722f9fb-32ce-11e6-b969-2c44fd7f4dcc, 2d9d8356-3c3a-11e7-b969-2c44fd7f4dcc, 8ace17bd-82b7-11e5-9ecc-1051721c3a3e, 1894f8c2-8d2f-11e7-b969-2c44fd7f4dcc, 3864f5dd-6dca-11e6-b969-2c44fd7f4dcc, 1fb1d041-7215-11e7-b969-2c44fd7f4dcc, 7584c32e-a8a5-11e5-b969-2c44fd7f4dcc, 546a1b2d-6dca-11e6-b969-2c44fd7f4dcc, 28d52c83-83e7-11e7-b969-2c44fd7f4dcc, 2f0ea128-a4bc-11e6-b969-2c44fd7f4dcc, 8c81c820-b020-11e5-b969-2c44fd7f4dcc, c8ac5658-abd0-11e7-b969-2c44fd7f4dcc, 40ba611f-7a39-11e6-b969-2c44fd7f4dcc, e5e7a23b-9bff-11e5-8645-008cfae40e8c, 17558fb4-beac-11e7-acae-70106fb15706, 9ff67591-255c-402f-bdda-0e5400bef5d2, 2772be99-dabf-11e5-b969-2c44fd7f4dcc, d715c3ea-3736-11e5-9ecc-1051721c3a3e, 1792cdd4-6e10-11e7-b969-2c44fd7f4dcc, 502e931c-c337-11e5-b969-2c44fd7f4dcc, c1dd81ad-61c0-4626-a182-eb9173c48e1a, 3ebfdb75-71a4-11e5-9ecc-1051721c3a3e, b208da89-0bd1-11e7-b969-2c44fd7f4dcc, a0d1af05-fca6-11e6-b969-2c44fd7f4dcc, 641cff70-ee53-4848-8802-455052e26279, aa23e049-daa8-11e5-b969-2c44fd7f4dcc, 0dbbd71b-3104-11e5-9ecc-1051721c3a3e, 1b594129-aaf7-11e5-b969-2c44fd7f4dcc, b6ddf5a6-0d12-11e7-b969-2c44fd7f4dcc, e2552a37-bfaa-491a-98ff-f923d7b4461f, d682d7c1-171d-11e5-9ecc-1051721c3a3e, 2f59d420-bf57-11e6-b969-2c44fd7f4dcc, bc385643-8927-11e5-9ecc-1051721c3a3e, da5c2e88-5d68-11e7-b969-2c44fd7f4dcc, b01d6e52-5d83-4b6a-b0e1-2510a69c6c29, d5e9a2e0-1a91-11e7-b969-2c44fd7f4dcc, e5a12e68-ba17-11e6-b969-2c44fd7f4dcc, 8f66ebb8-0aee-4859-a8c7-4cad3eb89489, 4fc9b396-64d0-11e5-9ecc-1051721c3a3e, 02d2e16d-4cd0-11e7-b969-2c44fd7f4dcc, 5bc8d9a9-6d8e-48fe-874e-88ae57faef99, 55063631-88ca-49c7-b332-3241647d5605, 14fa5f69-6d72-11e5-9ecc-1051721c3a3e, 0ee7061b-44c3-11e5-9ecc-1051721c3a3e, 36fa3ecb-a583-11e7-b969-2c44fd7f4dcc, 82500500-7255-11e5-9ecc-1051721c3a3e, 3d7d1bf6-09d4-11e5-b70f-1051721c3a3e, ed17abc2-094c-11e7-b969-2c44fd7f4dcc, bae57ddc-beb6-11e7-acae-70106fb15706, 0e6ac98a-5c57-11e5-9ecc-1051721c3a3e, 8b239e27-038c-4116-8856-a35c57a42228, afef694a-7d97-11e7-b969-2c44fd7f4dcc, 4f8a8c17-a195-4c7e-8392-2d233e37ff95, f5d717f3-f02a-11e6-b969-2c44fd7f4dcc, 52d3e792-5e1c-4cfb-964f-3a5e9915a2fd, 987e50fe-2c5d-11e6-b969-2c44fd7f4dcc, bef03c07-3d07-11e6-b969-2c44fd7f4dcc, 6ffb8157-6abd-11e6-b969-2c44fd7f4dcc, 56b681c8-2939-11e6-b969-2c44fd7f4dcc, 97492431-6124-11e7-b969-2c44fd7f4dcc, 9214600f-69ce-11e7-b969-2c44fd7f4dcc, d0b1ad63-0faf-11e7-b969-2c44fd7f4dcc, d84f43fb-343a-11e5-9ecc-1051721c3a3e, f22584ff-3d41-11e7-b969-2c44fd7f4dcc, 5794b317-5549-11e5-9ecc-1051721c3a3e, 6424123e-0790-11e6-b969-2c44fd7f4dcc, 1033464e-3053-11e5-9ecc-1051721c3a3e, 10494357-1d6e-11e6-b969-2c44fd7f4dcc, 18da70d3-4c19-444d-a559-c04b69206152, 69638a0e-8be1-11e7-b969-2c44fd7f4dcc, c668323e-3988-11e5-9ecc-1051721c3a3e, 1bce94a5-d619-11e5-b969-2c44fd7f4dcc, a0637eb3-f294-11e6-b969-2c44fd7f4dcc, 45c7c93a-2480-43aa-8204-aee631234cad, 50af920e-9f19-11e5-8645-008cfae40e8c, 172fe216-07f5-11e7-b969-2c44fd7f4dcc, 708bf1fc-7bd6-11e7-b969-2c44fd7f4dcc, e21b25ec-32fe-4ee8-8b53-76606f9bfbde, 0f2b966f-bd6d-11e7-acae-70106fb15706, e2bcb3d7-b569-11e7-b969-2c44fd7f4dcc, c479b863-be07-11e7-acae-70106fb15706, 3835458f-52b4-11e5-9ecc-1051721c3a3e, 61a76ab8-d61f-11e5-b969-2c44fd7f4dcc, cfafe9bf-067a-11e5-b70f-1051721c3a3e, 739998a2-f583-11e6-b969-2c44fd7f4dcc, cd139475-4a27-11e5-9ecc-1051721c3a3e, db60e8cb-4fa0-11e6-b969-2c44fd7f4dcc, ee9df739-cbda-11e6-b969-2c44fd7f4dcc, d472a139-7197-11e5-9ecc-1051721c3a3e, 0838d4b4-ef07-11e4-92e6-1051721c3a3e, d5e77e14-b6d4-11e6-b969-2c44fd7f4dcc, 9ce5e970-b7dd-11e7-acae-70106fb15706, 2197451e-4b12-11e5-9ecc-1051721c3a3e, cfb9765c-8c8d-11e6-b969-2c44fd7f4dcc, 3cb4489c-bd61-11e7-acae-70106fb15706, 25124d94-8e1f-11e7-b969-2c44fd7f4dcc, dcff7ae6-7be6-11e7-b969-2c44fd7f4dcc, 4d62714b-1ca8-11e5-9ecc-1051721c3a3e, 096a9421-4482-11e5-9ecc-1051721c3a3e, 110536db-1a63-11e7-b969-2c44fd7f4dcc, 7bcceb75-2a0d-4c2f-95ba-ecacfab3e45d, 1b1b6ed5-7338-11e5-9ecc-1051721c3a3e, a861c0da-879a-11e7-b969-2c44fd7f4dcc, ad9fabcf-befe-11e7-acae-70106fb15706, 6bab95e4-4a28-11e6-b969-2c44fd7f4dcc, 378c3134-0a42-11e7-b969-2c44fd7f4dcc, 9ed6bf43-a48c-11e5-8645-008cfae40e8c, 70bfb404-76c7-4dd9-bc0e-6b8f8fd21590, 295c571d-6dca-11e6-b969-2c44fd7f4dcc, 3ef27d45-bd7b-11e7-acae-70106fb15706, 65ff539b-31f6-11e5-9ecc-1051721c3a3e, d7557e71-5691-11e7-b969-2c44fd7f4dcc";
		String[] arr = tmp.split(",");
		StringBuilder sb = new StringBuilder();
		for(String s : arr) {
			sb.append("'").append(s.trim()).append("'").append(",");
		}
		System.out.println(sb.substring(0, sb.toString().length() - 1));
	}
	
}
