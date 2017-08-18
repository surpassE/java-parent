import com.framework.redis.annotation.DistributeLock;

public class TestIocImpl implements TestIoc {

	public String msg;

	@Override
	@DistributeLock(key = "")
	public void showMsg() {
		System.out.println("");
	}

}
