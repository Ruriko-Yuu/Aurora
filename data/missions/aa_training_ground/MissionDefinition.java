package data.missions.aa_training_ground;

import com.fs.starfarer.api.fleet.FleetGoal;
import com.fs.starfarer.api.fleet.FleetMemberType;
import com.fs.starfarer.api.mission.FleetSide;
import com.fs.starfarer.api.mission.MissionDefinitionAPI;
import com.fs.starfarer.api.mission.MissionDefinitionPlugin;

public class MissionDefinition implements MissionDefinitionPlugin {
    @Override
    public void defineMission(MissionDefinitionAPI api) {
        // 设置舰队，以便我们可以为它们添加战舰和战斗机联队。
        // 在这种情况下，舰队正在互相攻击，但在其他情况下，舰队可能正在防御或试图逃跑
        api.initFleet(FleetSide.PLAYER, "DSF", FleetGoal.ATTACK, false);
        api.initFleet(FleetSide.ENEMY, "HSS", FleetGoal.ATTACK, true);

        // 为显示在任务详细信息和任务结果屏幕上的每个舰队设置一个小简介，以识别每一方。
        api.setFleetTagline(FleetSide.PLAYER, "PLAYER");
        api.setFleetTagline(FleetSide.ENEMY, "ENEMY");

        // 这些项目出现在任务细节内战术目标下的项目列表中
        api.addBriefingItem("发射微型导弹使敌舰 PD 系统趋于饱和.");
        api.addBriefingItem("您的护盾并不出色, 请谨慎控制幅能水平.");
        api.addBriefingItem("HeadOn 必须存活");

        // 设置玩家的舰队。
        // 变量名称来自data/variants和data/variants/Fighter中的文件
        api.addToFleet(FleetSide.PLAYER, "aurora_marisa", FleetMemberType.SHIP, true);

        // 将两艘飞船都标记为必不可少的——失去其中一艘会导致任务失败。
        // 也可能被设置在敌舰上，在这种情况下，摧毁它将导致胜利。
        api.defeatOnShipLoss("CNC HeadOn");

        // 设置敌方的舰队。
        api.addToFleet(FleetSide.ENEMY, "condor_Support", FleetMemberType.SHIP, false);

        // 设置地图
        float width = 15000f;
        float height = 11000f;
        api.initMap((float)-width/2f, (float)width/2f, (float)-height/2f, (float)height/2f);

        float minX = -width/2;
        float minY = -height/2;

        // 所有addXXX方法都会获取一对坐标，后跟要添加的任何对象的数据。

        // 还有一些随机调节使场地增添趣味性
        // 类似的方法也可用于将其他所有东西随机化，包括舰队组成。
        for (int i = 0; i < 7; i++) {
            float x = (float) Math.random() * width - width/2;
            float y = (float) Math.random() * height - height/2;
            float radius = 100f + (float) Math.random() * 800f;
            api.addNebula(x, y, radius);
        }

        // 添加目标。(中继器)
        // 这些可以被每一方占领，并提供属性加成和额外的指挥点来增援。
        // 增援只对大型舰队很重要 —— 在这种情况下，假设战斗规模为 100 部署点，两个舰队都将能够立即完全部署。

        api.addObjective(minX + width * 0.7f, minY + height * 0.25f, "sensor_array");
        api.addObjective(minX + width * 0.8f, minY + height * 0.75f, "nav_buoy");
        api.addObjective(minX + width * 0.2f, minY + height * 0.25f, "nav_buoy");
    }
}
