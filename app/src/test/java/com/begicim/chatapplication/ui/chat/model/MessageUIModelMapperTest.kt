package com.begicim.chatapplication.ui.chat.model

import com.begicim.chatapplication.database.table.Message
import org.junit.Assert.assertEquals
import org.junit.Test

/*
* Message list order by message.id desc to show last message first
* */
class MessageUIModelMapperTest {

    @Test
    fun `when toUIModel called should format Date correctly`() {
        val messageList = listOf(Message(1, "test", 1, 2, "2021-10-04T13:33"))

        val uiMapper = MessageUIModelMapper()

        val expectedList =
            listOf(
                MessageUIModel("test", "Monday, 13:33", isSender = true, shouldShowTheData = true))

        assertEquals(expectedList, uiMapper.toUIModel(1, messageList))
    }

    @Test
    fun `when toUIModel called if fromId equals to message fromId isSender should be true`() {
        val messageList = listOf(
            Message(2, "test-2", 2, 1, "2021-10-05T13:33"),
            Message(1, "test", 1, 2, "2021-10-04T13:33")
        )

        val uiMapper = MessageUIModelMapper()

        val expectedList =
            listOf(
                MessageUIModel("test-2", "Tuesday, 13:33", isSender = false, shouldShowTheData = true),
                MessageUIModel("test", "Monday, 13:33", isSender = true, shouldShowTheData = true))
        assertEquals(expectedList, uiMapper.toUIModel(1, messageList))
    }

    @Test
    fun `when toUIModel called if previous message sent before one hour message date should be shown`(){
        val messageList = listOf(
            Message(4, "test-4", 2, 1, "2021-10-05T17:33"),
            Message(3, "test-3", 1, 2, "2021-10-04T14:33"),
            Message(2, "test-2", 2, 1, "2021-10-04T13:53"),
            Message(1, "test", 1, 2, "2021-10-04T13:33")
        )

        val uiMapper = MessageUIModelMapper()

        val expectedList =
            listOf(
                MessageUIModel("test-4", "Tuesday, 17:33", isSender = false, shouldShowTheData = true),
                MessageUIModel("test-3", "Monday, 14:33", isSender = true, shouldShowTheData = false),
                MessageUIModel("test-2", "Monday, 13:53", isSender = false, shouldShowTheData = false),
                MessageUIModel("test", "Monday, 13:33", isSender = true, shouldShowTheData = true))

        assertEquals(expectedList, uiMapper.toUIModel(1, messageList))
    }
}