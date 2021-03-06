package com.megagao.production.ssm.controller.scheduling;

import java.util.List;

import javax.validation.Valid;

import com.megagao.production.ssm.domain.customize.CustomResult;
import com.megagao.production.ssm.domain.Task;
import com.megagao.production.ssm.domain.customize.EUDataGridResult;
import com.megagao.production.ssm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;
	
	@RequestMapping("/get/{empId}")
	@ResponseBody
	public Task getItemById(@PathVariable String empId) throws Exception{
		Task task = taskService.get(empId);
		return task;
	}
	
	@RequestMapping("/find")
	public String find() throws Exception{
		return "task_list";
	}
	
	@RequestMapping("/get_data")
	@ResponseBody
	public List<Task> getData() throws Exception{
		return taskService.find();
	}
	
	@RequestMapping("/add")
	public String add() throws Exception{
		return "task_add";
	}
	
	@RequestMapping("/edit")
	public String edit() throws Exception{
		return "task_edit";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page, Integer rows, Task task) throws Exception{
		EUDataGridResult result = taskService.getList(page, rows, task);
		return result;
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	@ResponseBody
	private CustomResult insert(@Valid Task task, BindingResult bindingResult) throws Exception{
		CustomResult result;
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			return CustomResult.build(100, fieldError.getDefaultMessage());
		}
		if(taskService.get(task.getTaskId()) != null){
			result = new CustomResult(0, "??????????????????????????????????????????????????????????????????", null);
		}else{
			result = taskService.insert(task);
		}
		return result;
	}
	
	@RequestMapping(value="/update")
	@ResponseBody
	private CustomResult update(@Valid Task task, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			return CustomResult.build(100, fieldError.getDefaultMessage());
		}
		return taskService.update(task);
	}
	
	@RequestMapping(value="/update_all")
	@ResponseBody
	private CustomResult updateAll(@Valid Task task, BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()){
			FieldError fieldError = bindingResult.getFieldError();
			return CustomResult.build(100, fieldError.getDefaultMessage());
		}
		return taskService.updateAll(task);
	}
	
	@RequestMapping(value="/delete")
	@ResponseBody
	private CustomResult delete(String id) throws Exception {
		CustomResult result = taskService.delete(id);
		return result;
	}
	
	@RequestMapping(value="/delete_batch")
	@ResponseBody
	private CustomResult deleteBatch(String[] ids) throws Exception {
		CustomResult result = taskService.deleteBatch(ids);
		return result;
	}
	
	//??????????????????id??????
	@RequestMapping("/search_task_by_taskId")
	@ResponseBody
	public EUDataGridResult searchTaskByTaskId(Integer page, Integer rows, String searchValue) 
			throws Exception{
		EUDataGridResult result = taskService.searchTaskByTaskId(page, rows, searchValue);
		return result;
	}
	
	//????????????id??????
	@RequestMapping("/search_task_by_taskWorkId")
	@ResponseBody
	public EUDataGridResult searchTaskByTaskWorkId(Integer page, Integer rows, String searchValue) throws Exception{
		EUDataGridResult result = taskService.searchTaskByTaskWorkId(page, rows, searchValue);
		return result;
	}
	
	//??????????????????id??????
	@RequestMapping("/search_task_by_taskManufactureSn")
	@ResponseBody
	public EUDataGridResult searchTaskByTaskManufactureSn(Integer page, Integer rows, String searchValue) 
			throws Exception{
		EUDataGridResult result = taskService.searchTaskByTaskManufactureSn(page, rows, searchValue);
		return result;
	}
}
