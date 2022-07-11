package org.csu.laomall.common;

public class AlipayConst {
    public final static String APP_ID = "2021000121619901";//沙箱支付宝环境发起支付请求的应用ID
    //生成的应用私钥
    public final static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDfkoIIdKug+PRjdsj8nH/riVb/cGBB6u9u8tcBeH7fPjtttg+9S6+bXarJdfOf7mkxbSXNHDIAYjBJ1CBZooMqhEhcxJa1lGhC/ayeVxal1WYjhMHhLkZrzNKX4fsPCu3Sp7d8qi5R40CCXIKHT6lTUHRZv7zGTOzI1vvh/0hrsoTVOefiZYNNMMx0BQEig0FoCdLa0K98rR3kD6uTE7LoO3bcSwTjle7fyElITcJXGtnh8TntNCkiZvFU4Qgrde/IiD7+4/xlGCv4ygCidQ8wbP23AMRyHPFHOfG+UJ49NowcUV71ZwIADX3SauaIJDQ3T/9Vj4Ztsa/y92wSJTvvAgMBAAECggEACgzJ75tfA7NtOPNkAYb/cRURZhYlHZF0BebAp35FJb/kaAAhIP8/+5nwTXIR64SKEgC4DEpSLRY82XTBp4XYo5aishjcNXlgXnKMyelcSUQYfUxAcuDjpYDljpJH+Nsi6a9EXkRAJMo0YifKbk60a9xX/pjR4ZAfrL+NPxF8reqY7iIA3DGt50mG+zjJRx/mc4ch/LFCXYxsP1VEcBCEp2YbSJHTbFps+tug1suipXQlGt0D1CijZPblnO2pnaUuoe1aB9mUgaaywzXc78G8RPUvMx6wp1VIdyrFiUsM7cT/UFXyIJNtdabZE3ctWQpept9A2N6EbPHznEOocUzE0QKBgQD1PKBvhIXfKv8DVUOOK/LDMPnlCwZcowQ9o56axe6zIFUJLXzmSr0tjGATjt6MTrgBEvUw6l0CI3x82ZfCz6i795lxyILrqFaw5ZktCjrCOwCnfyxfyaCE2h0nHDJtauDFBV0tyQm62qSk8LW0tZnY8hjActbHdLsNpRHge8Y3MwKBgQDpYnfXalcHolSx8LheSg60fSNDKQM8iMbJVP3cBpZswezMsJ7ZEEHJCPhP7joUDFCMxcNRYbEhS4i7Z7XYcwVQZVrn31d5xrswLWEg92iznHzVaNlz8zkusJx4P7LwnrlxHn3MJzBvi+v/yNVGqK4vXbqot1QBlSwdVRX4/rd4VQKBgDfkxohEQyLXMOI4l7oaqCz2x639ckjA2uGVnjuWa04mS1tZI8N1O2LbQLaUdIK0GlocUQgCEoq/PctS6LT9EW8uAqodUkGVz5Rn8B6E21mTixWh9aIrWUuOTuCwG4o2tt4Q1+jsjiwpUydIuZFjZpo8q4mJYwx4vX1hpw5sB4RjAoGBALvqljY0IIk8EtgHzFvXY4m+bMMp5bo7I1x++7zK7Rn695FE8VIzvdSCTXYGimsUE1OV9RO1BmuZpynGcuwqiO/X/F9RPN0HwDclgtyJeZuNeBJZzyWGhNdsS0CjgZAvLX/AnmfgciaO0mJtMhUnwsDOxnoMyy5PD2s61GtdpRrVAoGBAMm9mdvlTpKrK32JJVkyLoq429QuuaVwLT9rmyXmBENqCstlXUmOdkYat8qKt3OhagzpIuKsXOm4KIFWrekyBbqW7WCDHZ00D0t11LkM9sz9/D3I1M5svVDPqzZFu7IBk6/xzW4Hk1tjUhxJpd5Rh+zEZovm2enFXwDT/0h1GxHR";//生成的应用私钥
    //支付宝公钥
    public final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxT4PMv4jvX0eReOBSEBPyR57rojZT+CNIpR3G4OLVpsZHJIKbO8VnE+u39S+xHd78ITgvqJfa14rglDUjLVNpZa04D7yCfXH+LExFW1RYthGEoaMo0qHVYjS9jac9Tay8PTUMImIffeF8MU5j2tTmcFbWqZQvpttPRihBLCjRHvAm9y3LilkyhIWrOkPJvru2TVb66F9KTmTo6/1OiSBdHfxfT2+xTFhkwXYaVTriPFy2CEaVQXOCwbral2BrEz+VK8A2SAItDotSTR9sc/kP2rnwF4cOOKCYudd8HhOp4IvZlWhvTNh/YF0gvJSVhjWZvfyne/LJM6eXsY3+zgckwIDAQAB";//支付宝公钥
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    public final static String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";//沙箱请求网关地址
    public final static String CHARSET = "UTF-8";// 编码
    public final static String FORMAT = "JSON";// 返回格式
    public final static String SIGN_TYPE = "RSA2";// 签名方式RSA2
    //支付宝服务器异步通知页面路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    public final static String NOTIFY_URL = "http://1.116.147.57:8080/order/alipayCallback";// 异步通知页面
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    public final static String RETURN_URL = "http://www.baidu.com";// 同步通知页面
    //  public final static String RETURN_URL = "http://localhost:28089/alipay/alipayReturnNotice";//本地回调
    public final static int ALIPAY_TYPE = 1;//支付宝支  付类型为1 微信为2
}
