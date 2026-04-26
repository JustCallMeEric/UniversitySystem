# Context Bundle — 学生姓名模糊查询优化

## Source Index

| 来源文件 | 类型 | 说明 |
|---------|------|------|
| `需求文档.md` | 需求文档 | 本次优化的原始需求 |
| `mydocs/specs/2026-04-19_00-00_学生姓名模糊查询.md` | 历史 Spec | 该功能的初始实现 Spec |
| `mydocs/archive/2026-04-19_00-00_学生姓名模糊查询_human.md` | 历史归档 | 该功能的实现摘要与关键决策 |

---

## Requirement Snapshot

> 针对根据学生姓名模糊查询学生信息的功能，提出以下优化：
> 1. 学生姓名为必填，如果未录入，提示：请输入学生姓名；
> 2. 查询返回的学生信息中，需要新增学院名称；

---

## Requirement Facts

| # | 事实 | 来源 |
|---|------|------|
| 1 | 目标功能为已有功能的**优化/增强**，非全新开发 | 需求文档措辞 + 历史 Spec |
| 2 | 姓名参数当前为**非必填**（Spring 默认行为），传入空字符串会导致 SQL 条件变为 `LIKE '%%'` | 历史 Spec + 代码现状 |
| 3 | 当前查询 SQL 仅返回 `student_id, name, age`，未返回 `college_name` | `StudentMapper.xml:42-47` |
| 4 | 当前 Service 层为纯透传，无任何校验或后处理 | `StudentServiceImpl.java:74-77` |
| 5 | `Student` 实体类已包含 `collegeName` 属性，`resultMap` 已定义其映射 | `StudentMapper.xml:6-15` |
| 6 | `selectByStudentId` 已实现 LEFT JOIN 查询学院名称的参考模式 | `StudentMapper.xml:23-28` |

---

## Business Rules

| # | 规则 | 说明 |
|---|------|------|
| BR-1 | 姓名字段**必填** | 请求参数 `name` 不得为空、不得为仅含空白字符的字符串 |
| BR-2 | 未录入时**明确提示** | 返回提示文案：`请输入学生姓名` |
| BR-3 | 返回字段需**包含学院名称** | 查询结果中 `collegeName` 应填充对应学院名称，而非 `null` |
| BR-4 | 模糊查询行为保持不变 | 仍使用 `LIKE '%name%'` 全模糊匹配 |

---

## Acceptance Criteria

| # | 验收标准 | 验证方式 |
|---|---------|---------|
| AC-1 | `GET /api/students/search`（无 `name` 参数）返回错误提示 | 接口测试：HTTP 400 + 提示文案 |
| AC-2 | `GET /api/students/search?name=`（空字符串）返回错误提示 | 接口测试：HTTP 400 + 提示文案 |
| AC-3 | `GET /api/students/search?name=张` 返回匹配学生列表，且每项包含 `collegeName` | 接口测试：HTTP 200 + JSON 包含非空 `collegeName` |
| AC-4 | 无匹配结果时仍返回空列表（行为不变） | 接口测试：HTTP 200 + `[]` |
| AC-5 | 学生无对应学院时，`collegeName` 显示为 `未查询到该学院`（与 `findByStudentId` 行为一致） | 接口测试： college 字段关联失败的记录 |

---

## Constraints

| # | 约束 | 说明 |
|---|------|------|
| C-1 | 技术栈锁定 | Spring Boot 2.7.18 + MyBatis 2.3.2，不可引入新依赖 |
| C-2 | 向后兼容 | 接口路径 `GET /api/students/search` 不变，现有调用方不应被破坏 |
| C-3 | 响应结构兼容 | `Student` 实体已包含 `collegeName`，返回 JSON 新增字段属于扩展而非 Breaking Change |
| C-4 | 数据库兼容 | SQL 需同时兼容 MySQL（生产）和 H2（测试） |

---

## Conflicts & Ambiguities

| # | 问题 | 分析 | 建议 |
|---|------|------|------|
| 1 | "未录入"是否包含仅含空格的字符串？ | 需求文档写"未录入"，字面意思为 `null` 或空字符串；但仅含空格也应视为无效输入 | 按**空/空白字符即无效**处理，更严谨 |
| 2 | `collegeName` 为空时提示文案 | 需求未明确，但 `findByStudentId` 已使用 `未查询到该学院` | 与现有 `findByStudentId` 行为保持一致 |

---

## Open Questions

| # | 问题 | 状态 |
|---|------|------|
| 1 | 校验失败返回 HTTP 400 还是 200 + 业务错误码？ | 待确认（Spring 默认校验返回 400，推荐沿用） |
| 2 | `name` 参数前后空格是否需要 trim 处理？ | 待确认（推荐 trim 后再校验和查询） |

---

## Next Actions

1. 确认 Open Questions 中的校验响应方式与 trim 策略
2. 进入 **Research** 阶段，分析现有 `findByStudentId` 的学院名称查询实现模式
3. 制定 **Plan**，明确修改文件、方法签名、原子 checklist
4. 执行 **Execute**，完成代码变更
5. **Review** 校验 Spec-Code 一致性与代码质量
