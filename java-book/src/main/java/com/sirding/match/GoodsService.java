package com.sirding.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoodsService {
	
	/**
	 * @Described			: 初始化物品
	 * @author				: zc.ding
	 * @date 				: 2016年12月26日
	 * @return
	 */
	public List<Goods> initGoods(){
		List<Goods> goodsList = new ArrayList<Goods>();
		for(int i = 0; i < 10; i++){
			Goods good = new Goods(100, 3, 0.0005);
			goodsList.add(good);
		}
		goodsList.add(new Goods(200.00, 3, 0.0005));
		goodsList.add(new Goods(500.00, 3, 0.0005));
		goodsList.add(new Goods(300.00, 3, 0.0005));
		goodsList.add(new Goods(600.00, 3, 0.0005));
		goodsList.add(new Goods(300.00, 3, 0.0005));
		for(Goods good : goodsList){
			System.out.println("物品容量：" + good.getCapacity());
		}
		return goodsList;
	}

	/**
	 * @Described			: 奇数
	 * @author				: zc.ding
	 * @date 				: 2016年12月26日
	 * @param list
	 * @return
	 */
	public List<Goods> oddList(List<Goods> list){
		return this.getResult(list, false);
	}
	
	/**
	 * @Described			: 偶数集合
	 * @author				: zc.ding
	 * @date 				: 2016年12月26日
	 * @param list
	 * @return
	 */
	public List<Goods> evenList(List<Goods> list){
		return this.getResult(list, true);
	}

	/**
	 * @Described			: 区分奇偶值， flag：true 获得偶数
	 * @author				: zc.ding
	 * @date 				: 2016年12月26日
	 * @param list
	 * @param flag
	 * @return
	 */
	private List<Goods> getResult(List<Goods> list, boolean flag){
		List<Goods> rsList = new ArrayList<Goods>();
		for(Goods good : list){
			if(flag && this.isOddOrEven(good.getCapacity())){
				rsList.add(good);
			}
			if(!flag && !this.isOddOrEven(good.getCapacity())){
				rsList.add(good);
			}
		}
		Collections.sort(rsList);
		return rsList;
	}
	
	/**
	 * @Described			: 判断num的奇偶性， 偶数:true, 奇数：false
	 * @author				: zc.ding
	 * @date 				: 2016年12月26日
	 * @param num
	 * @return
	 */
	private boolean isOddOrEven(double num){
		String tmp = String.valueOf(num).replaceAll("[0|\\.]", "");
		double n = Double.valueOf(tmp);
		if((n % 2) == 0){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		GoodsService service = new GoodsService();
//		service.isOddOrEven(1820.00);
		List<Goods> list = service.initGoods();
		List<Goods> oddList = service.oddList(list);
		for(Goods good : oddList){
			System.out.println(good.getCapacity());
		}
		System.out.println("========================");
		List<Goods> evenList = service.evenList(list);
		for(Goods good : evenList){
			System.out.println(good.getCapacity());
		}
	}
}
