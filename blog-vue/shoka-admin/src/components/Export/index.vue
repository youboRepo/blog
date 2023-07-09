<template>
    <el-dialog :title="title" v-model="dialogVisible" width="360px" @open="init">
        <el-radio v-model="exportType" :label="1">所有记录</el-radio>
        <el-radio v-model="exportType" :label="2">本页记录</el-radio>
        <el-radio v-model="exportType" :label="3">选中记录</el-radio>
        <template #footer>
            <el-row type="flex">
                <el-col :span="12" class="tl">
                    <el-checkbox v-if="showForImport" v-model="forImport" class="lh40">用于导入</el-checkbox>
                </el-col>
                <el-col :span="12">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="handleSubmit">确定</el-button>
                </el-col>
            </el-row>
        </template>
    </el-dialog>
</template>

<script>
  export default {
    name: 'ExportDialog',
    props: {
      title: {
        required: true,
        type: String
      },
      visible: [Boolean],
      showForImport: [Boolean]
    },
    data() {
      return {
        exportType: 2,
        forImport: false
      }
    },
    computed: {
      dialogVisible: {
        get() {
          return this.visible
        },
        set(visible) {
          this.$emit('update:visible', visible)
        }
      }
    },
    methods: {
      // 初始化值
      init() {
        this.exportType = 2
      },
      // 处理提交
      handleSubmit() {
        this.$emit('submit', this.exportType, this.forImport)
      }
    }
  }
  </script>