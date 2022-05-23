# 插入客户
insert into client value('360725198112157429','陈迎霞','13138503807','江西省赣州市崇义县','冯海森','16707916934','16707916934@qq.com','夫妻');
insert into client value('330921199205097618','左江','13138503807','江西省赣州市崇义县','张贵州','14659519890','14659519890@qq.com','朋友');
insert into client value('610321198406146412','周晓忠','18193972810','陕西省宝鸡市宝鸡县','张贵州','14659519890','14659519890@qq.com','朋友');
insert into client value('431022199402201711','郑子琪','15326562711','湖南省郴州市宜章县','张贵州','14659519890','14659519890@qq.com','朋友');
insert into client value('610322199403024215','陈晓晓','18193972810','陕西省宝鸡市宝鸡县','张贵州','14659519890','14659519890@qq.com','朋友');
insert into client value('330821198606055419','江会光','19973073396','浙江省衢州市衢县','张贵州','14659519890','14659519890@qq.com','朋友');

# 插入银行
insert into bank value('中国银行','上海市','19198100');
insert into bank value('中国建设银行','北京市','1919182100');
# 插入员工
insert into employee value('460026198504057015','张玉祥','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('430525197108235319','赵富良','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('440421199110125119','张博毅','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('610326197908232414','朱兴金','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('360721197407191210','陈业基','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('360721199403024215','陈兴金','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('320821197311105013','崔海德','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('211224198711047525','正莲','中国银行','19323992414','浙江省衢州市衢县');
insert into employee value('140431198910143523','王日梅','中国银行','19323992414','浙江省衢州市衢县');

insert into employee value('140431197812105414','周想想','中国建设银行','19323992414','浙江省衢州市衢县');
insert into employee value('441827199308152818','周德军','中国建设银行','19323992414','浙江省衢州市衢县');
insert into employee value('430922199912135628','钟春香','中国建设银行','19323992414','浙江省衢州市衢县');
insert into employee value('140221198205255112','刘东友','中国建设银行','19323992414','浙江省衢州市衢县');
insert into employee value('320925197104241515','应亮','中国建设银行','19323992414','浙江省衢州市衢县');
insert into employee value('140121197510065614','郑祥瑞','中国建设银行','19323992414','浙江省衢州市衢县');
insert into employee value('430124197507103112','贺德成','中国建设银行','19323992414','浙江省衢州市衢县');

# 插入部门
insert into department value('1900','会计部','后勤');
insert into department value('1901','财务部','后勤');
insert into department value('1902','人事管理部','后勤');
insert into department value('1903','法律事务部','支持');

# 插入部门管理
insert into manage_department value('中国银行','1900','460026198504057015');
insert into manage_department value('中国银行','1901','430525197108235319');
insert into manage_department value('中国银行','1902','440421199110125119');
insert into manage_department value('中国银行','1903','610326197908232414');
insert into manage_department value('中国建设银行','1900','140431197812105414');
insert into manage_department value('中国建设银行','1901','441827199308152818');
insert into manage_department value('中国建设银行','1902','430922199912135628');
insert into manage_department value('中国建设银行','1903','140221198205255112');

# 插入员工工作
insert into employee_work value('460026198504057015','1900','2000-01-25');
insert into employee_work value('430525197108235319','1900','2001-01-25');
insert into employee_work value('440421199110125119','1901','2002-01-25');
insert into employee_work value('610326197908232414','1901','2003-01-25');
insert into employee_work value('360721197407191210','1902','2003-02-25');
insert into employee_work value('360721199403024215','1902','2003-03-25');
insert into employee_work value('320821197311105013','1902','2003-04-25');
insert into employee_work value('211224198711047525','1903','2003-05-25');
insert into employee_work value('140431198910143523','1903','2003-06-25');

insert into employee_work value('140431197812105414','1900','2004-01-25');
insert into employee_work value('441827199308152818','1900','2005-01-25');
insert into employee_work value('430922199912135628','1901','2006-01-25');
insert into employee_work value('140221198205255112','1901','2007-01-25');
insert into employee_work value('320925197104241515','1902','2008-01-25');
insert into employee_work value('140121197510065614','1902','2009-01-25');
insert into employee_work value('430124197507103112','1902','2010-01-25');

# 插入账户
insert into account value('1');
insert into account value('2');
insert into account value('3');
insert into account value('4');

# 插入储蓄账户
insert into saving_account value('1','中国银行','360725198112157429',100,'2010-01-25 12:00:00',0,"人民币");
insert into saving_account value('2','中国建设银行','330921199205097618',200,'2010-02-25 12:00:00',0.11,"人民币");

# 插入支票账户
insert into check_account value('3','中国银行','610321198406146412',100,'2010-03-25 12:00:00',0);
insert into check_account value('4','中国建设银行','610321198406146412',200,'2010-04-25 12:00:00',0);


# 插入客户与员工对接
insert into employee_client value('360725198112157429','460026198504057015','账户负责人');
insert into employee_client value('330921199205097618','430525197108235319','账户负责人');
insert into employee_client value('610321198406146412','440421199110125119','贷款负责人');
insert into employee_client value('610321198406146412','610326197908232414','账户负责人');

# 插入客户访问账户
insert into client_account value('360725198112157429','1','2015-03-25 12:00:00');
insert into client_account value('330921199205097618','2','2015-04-25 12:00:00');

# loan
insert into loan value('1','中国建设银行','2000');
insert into loan value('2','中国建设银行','3000');
insert into loan value('3','中国建设银行','4000');
insert into loan value('4','中国银行','5000');
insert into loan value('5','中国银行','6000');
insert into loan value('6','中国银行','7000');

# client_get_loan
insert into client_get_loan value('330921199205097618','中国建设银行','1');
insert into client_get_loan value('610321198406146412','中国建设银行','2');
insert into client_get_loan value('330921199205097618','中国建设银行','3');
insert into client_get_loan value('610321198406146412','中国建设银行','1');
insert into client_get_loan value('330821198606055419','中国建设银行','2');
insert into client_get_loan value('330821198606055419','中国银行','4');
insert into client_get_loan value('330821198606055419','中国银行','5');
insert into client_get_loan value('330821198606055419','中国银行','6');

# loan_pay
insert into loan_pay value('中国建设银行','1','number1','2015-01-25 12:00:00','200');
insert into loan_pay value('中国建设银行','2','number1','2015-02-25 12:00:00','300');
insert into loan_pay value('中国银行','4','number1','2015-03-25 12:00:00','400');

