package com.kxmall.market.core.exception;

/**
 * Created by admin on 2019/7/1.
 */
public class ExceptionDefinition {

    public static final ServiceExceptionDefinition DEMO_MODE_NO_PERMISSION_OPERATION =
            new ServiceExceptionDefinition(-1, "演示模式，无权限操作!");

    public static final ServiceExceptionDefinition THIRD_PART_SERVICE_EXCEPTION =
            new ServiceExceptionDefinition(0, "第三方服务异常");

    public static final ServiceExceptionDefinition PLUGIN_EXCEPTION =
            new ServiceExceptionDefinition(1, "插件异常: ${0}");

    public static final ServiceExceptionDefinition THIRD_PART_IO_EXCEPTION =
            new ServiceExceptionDefinition(2, "第三方网络异常");

    public static final ServiceExceptionDefinition APP_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(10000, "系统未知异常");

    public static final ServiceExceptionDefinition PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(10002, "参数校验失败");

    public static final ServiceExceptionDefinition SYSTEM_BUSY =
            new ServiceExceptionDefinition(10007, "系统繁忙～");

    public static final ServiceExceptionDefinition OPERATION_TOO_OFTEN =
            new ServiceExceptionDefinition(10008, "操作太频繁啦，请稍后再试～");

    public static final ServiceExceptionDefinition USER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(11000, "用户系统未知异常");

    public static final ServiceExceptionDefinition USER_SEND_VERIFY_FAILED =
            new ServiceExceptionDefinition(11001, "发送验证码失败");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_EXIST =
            new ServiceExceptionDefinition(11002, "验证码未发送或已过期");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_CORRECT =
            new ServiceExceptionDefinition(11003, "验证码不正确");

    public static final ServiceExceptionDefinition USER_PHONE_HAS_EXISTED =
            new ServiceExceptionDefinition(11004, "手机已经被注册");

    public static final ServiceExceptionDefinition USER_PHONE_NOT_EXIST =
            new ServiceExceptionDefinition(11005, "手机尚未绑定账号");

    public static final ServiceExceptionDefinition USER_PHONE_OR_PASSWORD_NOT_CORRECT =
            new ServiceExceptionDefinition(11006, "手机号或密码错误!");

    public static final ServiceExceptionDefinition USER_THIRD_PART_LOGIN_FAILED =
            new ServiceExceptionDefinition(11007, "用户第三方登录失败");

    public static final ServiceExceptionDefinition USER_THIRD_UNEXPECT_RESPONSE =
            new ServiceExceptionDefinition(11008, "第三方登录期望之外的错误");

    public static final ServiceExceptionDefinition USER_THIRD_PART_NOT_SUPPORT =
            new ServiceExceptionDefinition(11009, "未知的第三方登录平台");

    public static final ServiceExceptionDefinition USER_INFORMATION_MISSING =
            new ServiceExceptionDefinition(11010, "用户信息缺失，不能添加");

    public static final ServiceExceptionDefinition USER_PHONE_ALREADY_EXIST =
            new ServiceExceptionDefinition(11011, "用户电话已经存在，不能添加");

    public static final ServiceExceptionDefinition USER_CAN_NOT_ACTICE =
            new ServiceExceptionDefinition(11012, "用户处于冻结状态，请联系管理员");

    public static final ServiceExceptionDefinition USER_WX_AUTH_PHONE =
            new ServiceExceptionDefinition(11013, "微信授权手机号失败");

    public static final ServiceExceptionDefinition USER_PHONE_NOT_BINDING_WX =
            new ServiceExceptionDefinition(11014, "用户未绑定手机号");

    public static final ServiceExceptionDefinition USER_WX_PHONE_PARSER_ERROR =
            new ServiceExceptionDefinition(11015, "微信授权手机号解析为空");

    public static final ServiceExceptionDefinition CART_UPDATE_FAILED =
            new ServiceExceptionDefinition(12001, "购物车更新失败");

    public static final ServiceExceptionDefinition ORDER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(13000, "订单系统未知异常");

    public static final ServiceExceptionDefinition ORDER_SKU_CANNOT_EMPTY =
            new ServiceExceptionDefinition(13001, "订单商品不能为空");

    public static final ServiceExceptionDefinition ORDER_SYSTEM_BUSY =
            new ServiceExceptionDefinition(13002, "订单系统繁忙~");

    public static final ServiceExceptionDefinition ORDER_SKU_STOCK_NOT_ENOUGH =
            new ServiceExceptionDefinition(13003, "商品库存不足～");

    public static final ServiceExceptionDefinition ORDER_SKU_NOT_EXIST =
            new ServiceExceptionDefinition(13004, "商品并不存在~");

    public static final ServiceExceptionDefinition ORDER_PRICE_MUST_GT_ZERO =
            new ServiceExceptionDefinition(13005, "订单金额必须大于0");

    public static final ServiceExceptionDefinition ORDER_PRICE_CHECK_FAILED =
            new ServiceExceptionDefinition(13006, "订单金额校验失败！");

    public static final ServiceExceptionDefinition ORDER_COUPON_NOT_EXIST =
            new ServiceExceptionDefinition(13007, "优惠券不存在或已使用！");

    public static final ServiceExceptionDefinition ORDER_COUPON_PRICE_NOT_ENOUGH =
            new ServiceExceptionDefinition(13008, "优惠券金额未达到指定值");

    public static final ServiceExceptionDefinition ORDER_COUPON_DISCOUNT_CHECK_FAILED =
            new ServiceExceptionDefinition(13009, "订单优惠券金额校验失败");

    public static final ServiceExceptionDefinition ORDER_ADDRESS_NOT_BELONGS_TO_YOU =
            new ServiceExceptionDefinition(13010, "收货地址不属于您！");

    public static final ServiceExceptionDefinition ORDER_NOT_EXIST =
            new ServiceExceptionDefinition(13011, "订单并不存在");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_PAY =
            new ServiceExceptionDefinition(13012, "订单状态不支持支付");

    public static final ServiceExceptionDefinition ORDER_STATUS_CHANGE_FAILED =
            new ServiceExceptionDefinition(13013, "订单状态流转失败！");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_REFUND =
            new ServiceExceptionDefinition(13014, "订单状态不支持退款");

    public static final ServiceExceptionDefinition ORDER_REFUND_FAILED =
            new ServiceExceptionDefinition(13015, "微信退款失败 请检查微信账户余额");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_CANCEL =
            new ServiceExceptionDefinition(13016, "订单状态不支持取消");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_CONFIRM =
            new ServiceExceptionDefinition(13016, "订单状态不支持确认收货");

    public static final ServiceExceptionDefinition ORDER_HAS_NOT_SHIP =
            new ServiceExceptionDefinition(13017, "订单尚未发货");

    public static final ServiceExceptionDefinition ORDER_DID_NOT_SET_SHIP =
            new ServiceExceptionDefinition(13018, "订单不需要物流公司");

    public static final ServiceExceptionDefinition ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY =
            new ServiceExceptionDefinition(13019, "当前平台不支持微信支付");

    public static final ServiceExceptionDefinition ORDER_DO_NOT_EXIST_SHIP_TRACE =
            new ServiceExceptionDefinition(13020, "暂时没有物流信息");

    public static final ServiceExceptionDefinition ORDER_PAY_CHANNEL_NOT_SUPPORT_REFUND =
            new ServiceExceptionDefinition(13021, "订单支付方式不支持退款");

    public static final ServiceExceptionDefinition ORDER_GROUP_SPU_CAN_SINGLE_TAKE =
            new ServiceExceptionDefinition(13022, "团购订单只允许单品结算");

    public static final ServiceExceptionDefinition ORDER_GROUP_SHOP_NOT_EXIST_OR_EXPIRED =
            new ServiceExceptionDefinition(13023, "团购活动已经过期或不存在");

    public static final ServiceExceptionDefinition ORDER_DISTRIBUTION_FAIL =
            new ServiceExceptionDefinition(13024, "订单分配操作异常，请稍后再试！");


    public static final ServiceExceptionDefinition COUPON_ISSUE_OVER =
            new ServiceExceptionDefinition(14001, "优惠券已经领完～");

    public static final ServiceExceptionDefinition COUPON_YOU_HAVE_OBTAINED =
            new ServiceExceptionDefinition(14002, "您已经领取过了～");

    public static final ServiceExceptionDefinition COUPON_ACTIVITY_NOT_START =
            new ServiceExceptionDefinition(14003, "优惠券活动还没开始");

    public static final ServiceExceptionDefinition COUPON_ACTIVITY_HAS_END =
            new ServiceExceptionDefinition(14004, "优惠券活动已经结束");

    public static final ServiceExceptionDefinition COUPON_HAS_LOCKED =
            new ServiceExceptionDefinition(14005, "优惠券活动已经冻结！");

    public static final ServiceExceptionDefinition COUPON_STRATEGY_INCORRECT =
            new ServiceExceptionDefinition(14006, "优惠券策略不正确");

    public static final ServiceExceptionDefinition COUPON_NOT_EXIST =
            new ServiceExceptionDefinition(14006, "优惠券不存在");

    public static final ServiceExceptionDefinition COUPON_CHECK_DATA_FAILED =
            new ServiceExceptionDefinition(14006, "优惠券审核数据失败");

    public static final ServiceExceptionDefinition COUPON_CREATE_HAS_ID =
            new ServiceExceptionDefinition(14007, "创建优惠券时请不要传入Id");

    public static final ServiceExceptionDefinition COUPON_UPDATE_NO_ID =
            new ServiceExceptionDefinition(14007, "更新优惠券时请传入Id");


    public static final ServiceExceptionDefinition COLLECT_ALREADY_EXISTED =
            new ServiceExceptionDefinition(15001, "该商品不允许重复收藏");

    public static final ServiceExceptionDefinition COLLECT_PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(15002, "收藏记录传入参数校验失败");


    public static final ServiceExceptionDefinition ADDRESS_QUERY_FAILED =
            new ServiceExceptionDefinition(16001, "这是个有地址却没有默认地址的用户");

    public static final ServiceExceptionDefinition ADDRESS_DATABASE_QUERY_FAILED =
            new ServiceExceptionDefinition(16002, "执行语句失败");

    public static final ServiceExceptionDefinition APPRAISE_PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(17001, "参数校验失败");

    public static final ServiceExceptionDefinition APPRAISE_ORDER_CHECK_FAILED =
            new ServiceExceptionDefinition(17002, "当前状态不允许评价");


    public static final ServiceExceptionDefinition FREIGHT_PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(18001, "大兄弟，你传给计算邮费的信息不对啊！");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_NOT_EXIST =
            new ServiceExceptionDefinition(18002, "运费模版不存在");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_INSERT_FAILED =
            new ServiceExceptionDefinition(18003, "运费模板主表增加失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_INSERT_FAILED =
            new ServiceExceptionDefinition(18004, "运费模板副表增加失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_DELETE_FAILED =
            new ServiceExceptionDefinition(18005, "运费模板主表删除失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_DELETE_FAILED =
            new ServiceExceptionDefinition(18006, "运费模板副表删除失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_UPDATE_FAILED =
            new ServiceExceptionDefinition(18007, "运费模板主表修改失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_UPDATE_FAILED =
            new ServiceExceptionDefinition(18008, "运费模板副表修改失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_QUERY_FAILED =
            new ServiceExceptionDefinition(18009, "运费模板主表查询失败");

    public static final ServiceExceptionDefinition FREIGHT_SPU_QUERY_HAS =
            new ServiceExceptionDefinition(18010, "当前仍有商品使用该模板");


    public static final ServiceExceptionDefinition FOOTPRINT_DELETE_FAILED =
            new ServiceExceptionDefinition(19001, "大兄弟，没有你传的足迹，或者你在误删他人足迹");

    public static final ServiceExceptionDefinition FOOTPRINT_SELECT_PARAM_CHECK =
            new ServiceExceptionDefinition(19002, "大兄弟，不会传分页数据，你就别传！有默认值");


    public static final ServiceExceptionDefinition GOODS_NOT_EXIST =
            new ServiceExceptionDefinition(20001, "商品并不存在");

    public static final ServiceExceptionDefinition GOODS_SKU_LIST_EMPTY =
            new ServiceExceptionDefinition(20002, "商品的类型（Sku）列表不能为空");

    public static final ServiceExceptionDefinition GOODS_CREATE_HAS_ID =
            new ServiceExceptionDefinition(20003, "创建商品时请不要传入Id");

    public static final ServiceExceptionDefinition GOODS_CREATE_BARCODE_REPEAT =
            new ServiceExceptionDefinition(20004, "商品条码已经存在了 商品Id:${0} 重复Sku:${1}");

    public static final ServiceExceptionDefinition GOODS_PRICE_CHECKED_FAILED =
            new ServiceExceptionDefinition(20005, "必须 vip价格 <= 现价 <= 原价");

    public static final ServiceExceptionDefinition GOODS_NEED_STATUS_ERROR =
            new ServiceExceptionDefinition(20006, "商品已经是该状态,无法改变");

    public static final ServiceExceptionDefinition GOODS_UPDATE_SQL_FAILED =
            new ServiceExceptionDefinition(20007, "商品执行修改SQL失败");


    public static final ServiceExceptionDefinition RECOMMEND_SPU_NO_HAS =
            new ServiceExceptionDefinition(21001, "你要加入推荐的商品不存在");

    public static final ServiceExceptionDefinition RECOMMEND_ALREADY_HAS =
            new ServiceExceptionDefinition(21002, "你要加入推荐的商品已推荐");

    public static final ServiceExceptionDefinition RECOMMEND_SQL_ADD_FAILED =
            new ServiceExceptionDefinition(21003, "加入推荐数据库失败");

    public static final ServiceExceptionDefinition RECOMMEND_SQL_DELETE_FAILED =
            new ServiceExceptionDefinition(21004, "删除推荐数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_ADD_FAILED =
            new ServiceExceptionDefinition(22001, "添加广告数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_DELETE_FAILED =
            new ServiceExceptionDefinition(22002, "删除广告数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_UPDATE_FAILED =
            new ServiceExceptionDefinition(22003, "修改广告数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_URL_NOT_EXIST =
            new ServiceExceptionDefinition(22004, "广告URL不能为空");

    public static final ServiceExceptionDefinition STORAGE_NOT_EXIST =
            new ServiceExceptionDefinition(23001, "前置仓资料不存在");

    public static final ServiceExceptionDefinition RIDER_PHONE_HAVED_EXIST =
            new ServiceExceptionDefinition(24001, "配送员手机号已经存在");

    public static final ServiceExceptionDefinition RIDER_PHONE_MUST_INOUT =
            new ServiceExceptionDefinition(24002, "配送员手机号不能为空");

    public static final ServiceExceptionDefinition RIDER_ORDER_STATUS_NOT_EXIST =
            new ServiceExceptionDefinition(24003, "配送员订单状态不存在");

    public static final ServiceExceptionDefinition RIDER_ORDER_NOT_EXIST =
            new ServiceExceptionDefinition(24004, "配送员订单不存在");

    public static final ServiceExceptionDefinition RIDER_ORDER_SPU_NOT_EXIST =
            new ServiceExceptionDefinition(24006, "配送员订单商品不存在");

    public static final ServiceExceptionDefinition RIDER_ORDER_UPDATE_FAIL =
            new ServiceExceptionDefinition(24007, "配送员订单状态更新异常，请稍后再试");

    public static final ServiceExceptionDefinition RIDER_ORDER_STATUS_NOT_MATCH =
            new ServiceExceptionDefinition(24008, "当前配送员订单状态不能执行该操作");

    public static final ServiceExceptionDefinition RIDER_ORDER_MESSAGE_SAVE_ERROR =
            new ServiceExceptionDefinition(24009, "配送订单消息保存失败");

    public static final ServiceExceptionDefinition RIDER_ORDER_SYSTEM_BUSY =
            new ServiceExceptionDefinition(24010, "配送订单系统繁忙~");

    public static final ServiceExceptionDefinition RIDER_ORDER_STATUS_CHANGE_FAILED =
            new ServiceExceptionDefinition(24011, "配送订单状态流转失败！");

    public static final ServiceExceptionDefinition RIDER_ORDER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(24012, "配送订单系统未知异常");

    public static final ServiceExceptionDefinition ORDER_RIDER_INFO_REDUCE =
            new ServiceExceptionDefinition(24013, "配送订单信息缺少");

    public static final ServiceExceptionDefinition ADMIN_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(50000, "管理员系统未知异常");

    public static final ServiceExceptionDefinition ADMIN_NOT_EXIST =
            new ServiceExceptionDefinition(50001, "管理员不存在");

    public static final ServiceExceptionDefinition ADMIN_PASSWORD_ERROR =
            new ServiceExceptionDefinition(50002, "密码错误");

    public static final ServiceExceptionDefinition ADMIN_NOT_BIND_WECHAT =
            new ServiceExceptionDefinition(50003, "管理员尚未绑定微信");

    public static final ServiceExceptionDefinition ADMIN_APPLY_NOT_BELONGS_TO_YOU =
            new ServiceExceptionDefinition(50004, "用户申请表并不属于您");

    public static final ServiceExceptionDefinition ADMIN_APPLY_NOT_SUPPORT_ONE_KEY =
            new ServiceExceptionDefinition(50005, "未定义类型不支持一键发布");

    public static final ServiceExceptionDefinition ADMIN_ROLE_IS_EMPTY =
            new ServiceExceptionDefinition(50006, "管理员角色为空！");

    public static final ServiceExceptionDefinition ADMIN_USER_NAME_REPEAT =
            new ServiceExceptionDefinition(50007, "管理员用户名重复");

    public static final ServiceExceptionDefinition ADMIN_VERIFYCODE_ERROR =
            new ServiceExceptionDefinition(50008, "登陆验证码错误");

    public static final ServiceExceptionDefinition ADMIN_USER_NOT_EXITS =
            new ServiceExceptionDefinition(50009, "管理员不存在，请输入正确账号密码");

    public static final ServiceExceptionDefinition ADMIN_GUEST_NOT_NEED_VERIFY_CODE =
            new ServiceExceptionDefinition(50010, "游客用户无须验证码，请直接输入666666");

    public static final ServiceExceptionDefinition ADMIN_VERIFY_CODE_SEND_FAIL =
            new ServiceExceptionDefinition(50011, "登陆验证码发送失败");

    public static final ServiceExceptionDefinition PROVINCE_IS_EMPTY =
            new ServiceExceptionDefinition(500015, "行政省为空！");

    public static final ServiceExceptionDefinition CITY_IS_EMPTY =
            new ServiceExceptionDefinition(500016, "行政市为空！");

    public static final ServiceExceptionDefinition COUNTY_IS_EMPTY =
            new ServiceExceptionDefinition(500017, "行政区（县）为空！");

    public static final ServiceExceptionDefinition CATEGORY_OUGHT_TO_EMPTY =
            new ServiceExceptionDefinition(51001, "该类目还有子类目或着商品");

    public static final ServiceExceptionDefinition CATEGORY_OR_PARENT_NODE_IS_EMPTY =
            new ServiceExceptionDefinition(51002, "传入ID，父节点ID不能为空");

    public static final ServiceExceptionDefinition PARENT_CAN_NOT_EQUALS_ONESELF =
            new ServiceExceptionDefinition(51003, "父节点不能是自己");

    public static final ServiceExceptionDefinition NOT_FIND_PARENT_NODE =
            new ServiceExceptionDefinition(51004, "未在数据库中查找到父节点");

    public static final ServiceExceptionDefinition CATEGORY_UPDATE_FAILURE =
            new ServiceExceptionDefinition(51005, "类目数据库修改失败");

    public static final ServiceExceptionDefinition PARENT_NODE_INFORMATION_ERROR =
            new ServiceExceptionDefinition(51006, "父节点信息不准确");

    public static final ServiceExceptionDefinition DATABASE_INSERT_FAILURE =
            new ServiceExceptionDefinition(51007, "数据库类目插入失败");

    public static final ServiceExceptionDefinition MAX_SECOND_CATEGORY_ERROR =
            new ServiceExceptionDefinition(51008, "最高两层类目，该类目层级不符合要求");


    public static final ServiceExceptionDefinition ORDER_EXCEL_PARAM_ERROR =
            new ServiceExceptionDefinition(52001, "生成excel查询参数错误");


    public static final ServiceExceptionDefinition SPU_NO_EXITS_OR_ONLY_SPU =
            new ServiceExceptionDefinition(53001, "团购商品中对应的spu不存在或只有spu存在,没有对应sku存在");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_NUMBER_ERROR =
            new ServiceExceptionDefinition(53002, "团购商品sku数量不对应");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_ID_ERROR =
            new ServiceExceptionDefinition(53003, "团购商品sku所对应的sku_id错误.");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_PRICE_ERROR =
            new ServiceExceptionDefinition(53004, "团购商品sku价格为空,或者为0");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_GROUP_SHOP_ID_ERROR =
            new ServiceExceptionDefinition(53005, "团购商品sku的团购商品spuID和传入的不一致");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_ADD_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(53006, "团购商品spu添加出误");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_ADD_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(53007, "团购商品sku添加出错");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_DELETE_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(53008, "团购商品spu删除出错");

    public static final ServiceExceptionDefinition GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(53009, "团购商品sku删除出错");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_NO_EXITS =
            new ServiceExceptionDefinition(53010, "团购商品spu不存在");

    public static final ServiceExceptionDefinition GROUP_SHOP_SPU_UPDATE_SQL_QUERY_ERROR =
            new ServiceExceptionDefinition(53011, "团购商品spu更新失败");

    public static final ServiceExceptionDefinition GROUP_SHOP_START_MUST_LESS_THAN_END =
            new ServiceExceptionDefinition(53012, "团购开始时间必须小于结束时间");

    public static final ServiceExceptionDefinition ORDER_IS_NOT_GROUP_SHOP_STATUS =
            new ServiceExceptionDefinition(53013, "订单状态不是团购状态");

    public static final ServiceExceptionDefinition GROUP_SHOP_ALREADY_EXIT =
            new ServiceExceptionDefinition(53014, "该商品已经是团购商品");

    public static final ServiceExceptionDefinition GROUP_SHOP_ALREAD_ATCIVE =
            new ServiceExceptionDefinition(53015, "团购商品已经在团购中.无法进行编辑或修改操作");

    public static final ServiceExceptionDefinition RIDER_NOT_EXIST =
            new ServiceExceptionDefinition(60001, "配送员不存在");

    public static final ServiceExceptionDefinition RIDER_UPDATE_ERROR =
            new ServiceExceptionDefinition(60002, "配送员信息更新异常");

    public static final ServiceExceptionDefinition GOODS_ID_NOT =
            new ServiceExceptionDefinition(60003, "参数不能为空");

    public static final ServiceExceptionDefinition GOODS_NOT_STOCK =
            new ServiceExceptionDefinition(60004, "更新商品库存失败");

    public static final ServiceExceptionDefinition GOODS_STOCK_FALSE =
            new ServiceExceptionDefinition(60005, "商品出库失败");

    public static final ServiceExceptionDefinition GOODS_DELETE =
            new ServiceExceptionDefinition(60006, "出库商品删除失败");

    public static final ServiceExceptionDefinition GOODS_OUT_INSERT =
            new ServiceExceptionDefinition(60007, "出库商品添加失败");

    public static final ServiceExceptionDefinition GOODS_OUT_Delete =
            new ServiceExceptionDefinition(60008, "出库商品更新失败");

    public static final ServiceExceptionDefinition GOODS_OUT_SPU_DELETE =
            new ServiceExceptionDefinition(60009, "出库商品删除失败");

    public static final ServiceExceptionDefinition GOODS_STORAGE_NAME =
            new ServiceExceptionDefinition(60010, "获取仓库名时出错");

    public static final ServiceExceptionDefinition ACTIVITY_COUPON_LIST_EMPTY =
            new ServiceExceptionDefinition(70001, "活动购物券列表不能为空");

    public static final ServiceExceptionDefinition ACTIVITY_CREATE_HAS_ID =
            new ServiceExceptionDefinition(70002, "创建活动时请不要传入Id");

    public static final ServiceExceptionDefinition ACTIVITY_START_MUST_LESS_THAN_END =
            new ServiceExceptionDefinition(70003, "活动开始时间必须小于结束时间");

    public static final ServiceExceptionDefinition SKU_NOT_EXIST_IN_STORAGE =
            new ServiceExceptionDefinition(70004, "商品在仓库中不存在");

    public static final ServiceExceptionDefinition ACTIVITY_NOT_EXIST_UNIQUE =
            new ServiceExceptionDefinition(70005, "同种活动类型正常状态下只允许唯一一种活动类型,请先把其他'正常状态'的活动关闭");

    public static final ServiceExceptionDefinition NEW_TIMES_GET =
            new ServiceExceptionDefinition(80001, "查询时报出错");

    public static final ServiceExceptionDefinition NEW_TIMES_UPDATE =
            new ServiceExceptionDefinition(80002, "更新时出错");

    public static final ServiceExceptionDefinition NEW_TIMES_INSERT =
            new ServiceExceptionDefinition(80002, "添加时出错");

    public static ServiceExceptionDefinition buildVariableException(ServiceExceptionDefinition definition, String... args) {
        String msg = definition.getMsg();
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("${" + i + "}", args[i]);
        }
        return new ServiceExceptionDefinition(definition.getCode(), msg);
    }


}
